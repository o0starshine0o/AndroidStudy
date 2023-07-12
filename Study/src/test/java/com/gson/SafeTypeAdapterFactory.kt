package com.gson

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken

class SafeTypeAdapterFactory(private val normalGson: Gson) : TypeAdapterFactory {
    override fun <T : Any?> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        return when (type.rawType) {
            GsonSubData::class.java -> SafeTypeAdapter(normalGson, gson, type)
//            GsonData::class.java -> SafeTypeAdapter(gson, type)
            else -> null
        }
    }
}