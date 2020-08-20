package com.protostar.download

import android.util.Log
import com.protostar.utils.writeCache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import rx.Observable
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit


class DownloadManager private constructor() : DownloadProgressListener {
    companion object {
        private val TAG = DownloadManager::class.java.simpleName
        private val manager by lazy { DownloadManager() }
        var baseUrl = "https://update.ps.iqugame.com"

        /**
         * 开始下载
         *
         * @param url 下载文件的url
         * @param savePath 保存文件的目录
         * @param listener 下载监听器
         */
        fun download(url: String, savePath: String, listener: ((String, Long, Long, Boolean) -> Unit)? = null) =
            download(url, savePath, object : ProgressListener {
                override fun progressChanged(url: String, read: Long, length: Long, done: Boolean) = listener?.invoke(url, read, length, done) ?: Unit
            })

        /**
         * 开始下载
         *
         * @param url 下载文件的url
         * @param savePath 保存文件的目录
         * @param listener 下载监听器
         */
        fun download(url: String, savePath: String, listener: ProgressListener? = null) =
            download(manager.infoMap[url]?.takeIf { info -> info.savePath == savePath } ?: DownloadInfo(url, savePath), listener)


        /**
         * 开始下载
         *
         * @param info 下载文件info
         */
        fun download(info: DownloadInfo, listener: ((String, Long, Long, Boolean) -> Unit)? = null) = download(info, object : ProgressListener {
            override fun progressChanged(url: String, read: Long, length: Long, done: Boolean) = listener?.invoke(url, read, length, done) ?: Unit
        })

        /**
         * 开始下载
         *
         * @param info 下载文件info
         */
        fun download(info: DownloadInfo, listener: ProgressListener? = null) = info.apply {
            manager.progressObserver = listener
            manager.downLoad(info)
        }

        /**
         * 暂停下载
         *
         * @param info 需要暂停下载文件的info
         */
        fun pause(info: DownloadInfo) = pause(info.url)

        /**
         * 暂停下载
         *
         * @param url 需要暂停下载文件的info
         */
        fun pause(url: String) = manager.subscribeMap.remove(manager.infoMap.remove(url))?.unsubscribe()
    }

    /**
     * 保存下载订阅器,用于暂停任务
     */
    private val subscribeMap = HashMap<DownloadInfo, Subscription>()

    /**
     * 保存下载信息
     */
    private val infoMap = HashMap<String, DownloadInfo>()

    /**
     * 下载进度监听器
     */
    private var progressObserver: ProgressListener? = null

    /**
     * 下载服务
     */
    private val service by lazy {
        val client = OkHttpClient.Builder().connectTimeout(8, TimeUnit.SECONDS).addInterceptor(DownloadInterceptor(this)).build()
        Retrofit.Builder().client(client).baseUrl(baseUrl).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build().create(DownLoadService::class.java)
    }

    override fun progress(url: String, read: Long, length: Long, done: Boolean) {
        infoMap[url]?.apply {
            Log.i(TAG, "url: $url, read: $read; contentLength: $length")
            readLength = read
            // 该方法仍然是在子线程，如果想要调用进度回调，需要切换到主线程，否则的话，会在子线程更新UI，直接错误
            // 如果断点续传，重新请求的文件大小是从断点处到最后的大小，不是整个文件的大小，info中的存储的总长度是
            // 整个文件的大小，所以某一时刻总文件的大小可能会大于从某个断点处请求的文件的总大小。此时read的大小为
            // 之前读取的加上现在读取的
            if (contentLength > length) {
                readLength += contentLength - length
            } else {
                contentLength = length
            }
            Observable.just(1).observeOn(AndroidSchedulers.mainThread()).subscribe {
                progressObserver?.progressChanged(url, readLength, contentLength, done)
            }
        }
    }

    private fun downLoad(info: DownloadInfo) {
        Log.i(TAG, "start download with info: $info")
        service.download("bytes=${info.readLength}-", info.url)
            .subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).retryWhen(RetryWhenNetworkException())
            .map { responseBody -> info.apply { writeCache(responseBody, this) } }
            .observeOn(AndroidSchedulers.mainThread()).subscribe(object : Subscriber<DownloadInfo>() {
                override fun onCompleted() {
                    infoMap.remove(info.url)
                    subscribeMap.remove(info)
                    Log.i(TAG, "onCompleted: $info")
                }

                override fun onError(e: Throwable) {
                    infoMap.remove(info.url)
                    subscribeMap.remove(info)
                    Log.i(TAG, "onError: $e")
                }

                override fun onNext(info: DownloadInfo) {
                    Log.i(TAG, "onNext: $info")
                }
            }).apply { infoMap[info.url] = info;subscribeMap[info] = this }
    }
}