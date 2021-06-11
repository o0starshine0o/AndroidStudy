package com.abelhu.androidstudy.matrix

import android.content.Context
import com.abelhu.androidstudy.utils.L
import com.tencent.matrix.plugin.DefaultPluginListener
import com.tencent.matrix.plugin.Plugin
import com.tencent.matrix.report.Issue

class MatrixListener(context: Context?) : DefaultPluginListener(context) {
    override fun onInit(plugin: Plugin?) {
        super.onInit(plugin)
        L.d("onInit:$plugin")
    }

    override fun onStart(plugin: Plugin?) {
        super.onStart(plugin)
        L.d("onInit:$plugin")
    }

    override fun onStop(plugin: Plugin?) {
        super.onStop(plugin)
        L.d("onInit:$plugin")
    }

    override fun onDestroy(plugin: Plugin?) {
        super.onDestroy(plugin)
        L.d("onInit:$plugin")
    }

    override fun onReportIssue(issue: Issue?) {
        super.onReportIssue(issue)
        L.d("onInit:$issue")
    }
}