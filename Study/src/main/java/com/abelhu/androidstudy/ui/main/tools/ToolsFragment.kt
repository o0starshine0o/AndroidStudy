package com.abelhu.androidstudy.ui.main.tools

//import com.tencent.tinker.lib.tinker.TinkerInstaller
//import com.tinkerpatch.sdk.TinkerPatch
import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Bundle
import android.telephony.*
import android.telephony.cdma.CdmaCellLocation
import android.telephony.gsm.GsmCellLocation
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.abelhu.androidstudy.R
import com.qicode.extension.TAG
import com.tbruyelle.rxpermissions3.RxPermissions
import kotlinx.android.synthetic.main.fragment_tools.*

class ToolsFragment : Fragment() {

    private lateinit var toolsViewModel: ToolsViewModel

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RxPermissions(this).setLogging(true)
        RxPermissions(this).request(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION).subscribe()
        RxPermissions(this).request(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION).subscribe { granted ->
            if (granted) Log.i(TAG(), "get permission")
            else Log.e(TAG(), "can not get permission")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        toolsViewModel = ViewModelProvider(this).get(ToolsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_tools, container, false).apply {
            toolsViewModel.fragmentInfo.observe(viewLifecycleOwner, { text_tools.text = it })
            toolsViewModel.baseStation.observe(viewLifecycleOwner, { info.text = it })
        }
    }

    @SuppressLint("SdCardPath")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        applyPatch.setOnClickListener { TinkerInstaller.onReceiveUpgradePatch(context, "/sdcard/Download/patch_signed.apk") }
//        downloadPatch.setOnClickListener { TinkerPatch.with().fetchPatchUpdate(true) }
//        deletePatch.setOnClickListener { TinkerPatch.with().cleanAll() }

        /**
         * 功能描述：通过手机信号获取基站信息
         * # 通过TelephonyManager 获取lac:mcc:mnc:cell-id
         * # MCC，Mobile Country Code，移动国家代码（中国的为460）；
         * # MNC，Mobile Network Code，移动网络号码（中国移动为0，中国联通为1，中国电信为2）；
         * # LAC，Location Area Code，位置区域码；
         * # CID，Cell Identity，基站编号；
         * # BSSS，Base station signal strength，基站信号强度。
         */
        getBaseStation.setOnClickListener {
            val telephony = context?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val operator = telephony.networkOperator
            var mcc = Int.MAX_VALUE
            var mnc = Int.MAX_VALUE
            var lac = Int.MAX_VALUE
            var cid = Int.MAX_VALUE
            var dbm = Int.MAX_VALUE

            when (val location = telephony.cellLocation) {
                is CdmaCellLocation -> {
                    cid = location.baseStationId
                    lac = location.networkId
                    mcc = telephony.networkOperator.substring(0, 3).toInt()
                    mnc = telephony.networkOperator.substring(3).toInt()
                }
                is GsmCellLocation -> {
                    cid = location.cid
                    lac = location.lac
                }
            }

            if (ActivityCompat.checkSelfPermission(it.context, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // 华为手机这里telephony.allCellInfo返回空值
                telephony.allCellInfo?.forEach { info ->
                    when (info) {
                        is CellInfoLte -> {
                            mcc = info.cellIdentity.mcc
                            mnc = info.cellIdentity.mnc
                            cid = info.cellIdentity.ci
                            lac = info.cellIdentity.tac
                            dbm = info.cellSignalStrength.dbm

                        }
                        is CellInfoCdma -> {
                            mcc = 0
                            mnc = 0
                            cid = info.cellIdentity.basestationId
                            lac = info.cellIdentity.networkId
                            dbm = info.cellSignalStrength.dbm
                        }
                        is CellInfoGsm -> {
                            cid = info.cellIdentity.cid
                        }
                        is CellInfoWcdma -> {
                            cid = info.cellIdentity.cid
                        }
                    }
                }
            }
            val message = """
BaseStation:
mcc(Mobile Country Code，移动国家代码,中国的为460):$mcc
mnc(Mobile Network Code，移动网络代码,中国移动为0，中国联通为1，中国电信为2):$mnc
lac(Location Area Code，位置区域码):$lac
cid(Cell Identity，基站编号):$cid
"""
            toolsViewModel.baseStation.postValue(message)
        }

        getWiFi.setOnClickListener {
            val wifi = context?.applicationContext?.getSystemService(Context.WIFI_SERVICE) as? WifiManager
            toolsViewModel.baseStation.postValue(wifi?.connectionInfo?.ssid)
        }

        getInstalledApk.apply { movementMethod = ScrollingMovementMethod.getInstance() }.setOnClickListener {
            val message = StringBuilder()
            context?.packageManager?.getInstalledPackages(0)?.forEach { message.append(it.packageName).append("\n") }
            toolsViewModel.baseStation.postValue(message.toString())
        }
    }
}