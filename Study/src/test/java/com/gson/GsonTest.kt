package com.gson

import com.google.gson.Gson
import org.junit.Test


class GsonTest {

    private val normalGson = Gson()
    private val safeFactory = SafeTypeAdapterFactory(normalGson)
    private val safeGson = normalGson.newBuilder().registerTypeAdapterFactory(safeFactory).create()

    @Test
    fun test0() {
        val json = "{\"data\":\"{\\\"data\\\": \\\"myData\\\"}\"}"

        val gsonData = safeGson.fromJson(json, GsonData::class.java)

        println(gsonData)
    }

    @Test
    fun test1() {
        val json = "{\"data\":{\"data\":\"myData\"}}"

        val gsonData = safeGson.fromJson(json, GsonData::class.java)

        println(gsonData)
    }
}