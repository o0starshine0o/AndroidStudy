package com.abelhu.androidstudy.utils

import android.content.Context
import android.net.wifi.WifiManager
import androidx.test.core.app.ApplicationProvider


class WiFiUtils {
    fun getSpeed(){
        val wifiManager: WifiManager = ApplicationProvider.getApplicationContext<Context>().getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        if (wifiInfo != null) {
            val linkSpeed = wifiInfo.linkSpeed //measured using WifiInfo.LINK_SPEED_UNITS
        }
    }
}