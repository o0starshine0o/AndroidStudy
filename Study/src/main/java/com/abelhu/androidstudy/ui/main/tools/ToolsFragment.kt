package com.abelhu.androidstudy.ui.main.tools

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.abelhu.androidstudy.R
import com.qicode.extension.TAG
import com.tbruyelle.rxpermissions2.RxPermissions
import com.tencent.tinker.lib.tinker.TinkerInstaller
import com.tinkerpatch.sdk.TinkerPatch
import kotlinx.android.synthetic.main.fragment_tools.*

class ToolsFragment : Fragment() {

    private lateinit var toolsViewModel: ToolsViewModel

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RxPermissions(this).request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe { granted ->
            if (granted) Log.i(TAG(), "get permission")
            else Log.e(TAG(), "can not get permission")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        toolsViewModel = ViewModelProvider(this).get(ToolsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_tools, container, false)
        toolsViewModel.text.observe(viewLifecycleOwner, Observer { text_tools.text = it })
        return root
    }

    @SuppressLint("SdCardPath")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyPatch.setOnClickListener { TinkerInstaller.onReceiveUpgradePatch(context, "/sdcard/Download/patch_signed.apk") }
        downloadPatch.setOnClickListener { TinkerPatch.with().fetchPatchUpdate(true) }
        deletePatch.setOnClickListener { TinkerPatch.with().cleanAll() }
    }
}