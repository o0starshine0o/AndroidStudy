package com.protostar.download

@FunctionalInterface
interface ProgressListener {
    fun progressChanged(url: String, read: Long, length: Long, done: Boolean)
}