package com.protostar.download

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class DownloadInterceptor(private val listener: DownloadProgressListener) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(chain.request()).let { response ->
        response.body?.let { body -> response.newBuilder().body(DownloadResponseBody(response.request.url.encodedPath, body, listener)).build() }
            ?: response
    }

}