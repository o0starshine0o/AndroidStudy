package com.protostar.download

import okhttp3.ResponseBody
import okio.*
import java.io.IOException


class DownloadResponseBody(val url: String, val responseBody: ResponseBody, val listener: DownloadProgressListener) : ResponseBody() {
    private val bufferedSource: BufferedSource = responseBody.source().let {
        object : ForwardingSource(it) {
            var totalBytesRead = 0L

            @Throws(IOException::class)
            override fun read(sink: Buffer, byteCount: Long) = super.read(sink, byteCount).apply {
                totalBytesRead += if (this != -1L) this else 0
                listener.progress(url, totalBytesRead, responseBody.contentLength(), this == -1L)
            }
        }
    }.buffer()

    override fun contentType() = responseBody.contentType()
    override fun contentLength() = responseBody.contentLength()
    override fun source() = bufferedSource

}
