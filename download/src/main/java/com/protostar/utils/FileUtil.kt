package com.protostar.utils

import com.protostar.download.DownloadInfo
import okhttp3.ResponseBody
import java.io.File
import java.io.IOException
import java.io.RandomAccessFile
import java.nio.channels.FileChannel


/**
 * 写入文件
 *
 * @param responseBody 网络请求返回的数据
 * @param info 下载文件的信息
 *
 * @throws IOException 读写文件发生的错误
 */
@Throws(IOException::class)
fun writeCache(responseBody: ResponseBody?, info: DownloadInfo) {
    responseBody?.apply {
        val file = File(info.savePath).apply { parentFile?.mkdirs() }
        // 注意,contentLength返回的是stream的长度,如果是断点续传,那么contentLength是需要加上之前的数据长度才是最终的文件大小
        val allLength = if (info.contentLength == 0L) contentLength() else info.contentLength
        RandomAccessFile(file, "rwd").use { randomAccessFile ->
            randomAccessFile.channel.use { channelOut ->
                val mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE, info.readLength, allLength - info.readLength)
                val buffer = ByteArray(1024 * 4)
                var length = 0
                byteStream().use { stream -> while (stream.read(buffer).apply { length = this } != -1) mappedBuffer.put(buffer, 0, length) }
            }
        }
    }
}