package com.protostar.download

interface DownloadProgressListener {
    /**
     * @param url 下载的url
     * @param read 已下载长度
     * @param length 总长度
     * @param done 是否下载完毕
     */
    fun progress(url: String, read: Long, length: Long, done: Boolean)
}