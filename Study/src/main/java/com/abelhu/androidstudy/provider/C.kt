/*
 * ************************************************************
 * file:C.kt  module:ArtSignPro.app  project:ArtSignPro
 * modify time:2021-05-11 17:33:18
 * author：Abelhu
 * Beijing QiCode Technology Co., Ltd.
 * Copyright (c) 2016-2021
 * ************************************************************
 *
 */

package com.abelhu.androidstudy.provider

import android.annotation.SuppressLint
import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.net.Uri

class C : ContentProvider() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        @JvmStatic
        lateinit var c: Context
    }

    override fun onCreate() = true.apply { context?.apply { c = this } }
    override fun query(p0: Uri, p1: Array<out String>?, p2: String?, p3: Array<out String>?, p4: String?): Nothing? = null
    override fun getType(p0: Uri): Nothing? = null
    override fun insert(p0: Uri, p1: ContentValues?): Nothing? = null
    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?) = 0
    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?) = 0
}