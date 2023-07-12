package com.abelhu.androidstudy.utils

import android.util.Log
import com.google.gson.Gson

const val TAG = "Json"

val gson by lazy { Gson() }

inline fun <reified T> String?.json(): T? {
    if (this == null) return null

    return try {
        return gson.fromJson(this, T::class.java)
    } catch (e: Exception) {
        Log.e(TAG, "json: $e")
        null
    }
}