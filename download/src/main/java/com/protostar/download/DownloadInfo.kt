package com.protostar.download

import com.google.gson.Gson

class DownloadInfo(var url: String, var savePath: String) {
    /* 文件总长度 */
    var contentLength: Long = 0

    /* 下载长度 */
    var readLength: Long = 0

    override fun toString() = Gson().toJson(this) ?: ""
}