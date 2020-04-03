package com.abelhu.androidstudy.binder.client

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.abelhu.androidstudy.aidl.AidlClient


/**
 * 假设这个是普通的Activity，主要使用bindService这个方法
 * 验证Binder的通信机制
 * 这种方法提供的沟通手段比较单一，只能通过transact进行沟通，如果服务器端的接口很多，这样调用起来不太方便，所以Android提供了一种更为简单的方式来实现-AIDL
 */
class NormalActivity : Activity() {

    init {
        // 需要和这个名字的Service绑定
        val intent = Intent("com.abelhu.androidstudy.binder.service.MyBinderService")
        bindService(intent, MyClient(), BIND_AUTO_CREATE)
        // 都是bindService，就一些小细节问题
        val aidlIntent = Intent().setAction("com.abelhu.androidstudy.aidl").setPackage("com.abelhu.androidstudy")
        bindService(aidlIntent, AidlClient.Instance, Context.BIND_AUTO_CREATE)
    }
}