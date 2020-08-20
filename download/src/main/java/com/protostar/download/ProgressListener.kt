package com.protostar.download


interface ProgressListener {
    fun progressChanged(url: String, read: Long, length: Long, done: Boolean)
}