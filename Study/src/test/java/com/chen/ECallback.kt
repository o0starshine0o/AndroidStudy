package com.chen

/**
 * Created by chenming on 2020-04-02
 * 通用的回调
 */
interface ECallback<in Any> {
    fun callback(data: Any)
}