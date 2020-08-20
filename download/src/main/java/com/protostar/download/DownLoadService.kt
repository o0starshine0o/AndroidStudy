package com.protostar.download

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Streaming
import retrofit2.http.Url
import rx.Observable


interface DownLoadService {
    /**
     * @param start 从某个字节开始下载数据
     * @param url   文件下载的url
     * @return Observable
     * @Streaming 这个注解必须添加，否则文件全部写入内存，文件过大会造成内存溢出
     */
    @Streaming
    @GET
    fun download(@Header("RANGE") start: String?, @Url url: String?): Observable<ResponseBody?>
}