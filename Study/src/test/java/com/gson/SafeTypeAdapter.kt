package com.gson

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

class SafeTypeAdapter<T>(
    private val normalGson: Gson,
    private val safeGson: Gson,
    private val type: TypeToken<T>
) : TypeAdapter<T>() {

    override fun read(reader: JsonReader?): T? {
        val data = when (reader?.peek()) {
            JsonToken.STRING -> safeGson.fromJson(reader.nextString(), type)
            else -> normalGson.fromJson(reader, type)
        }
        return data
    }

    override fun write(writer: JsonWriter?, value: T?) {
        if (writer == null) return
        when (value) {
            is String -> writer.value(value)
            is Number -> writer.value(value)
            is Boolean -> writer.value(value)
            else -> writer.nullValue()
        }
    }
}