package com.abelhu.androidstudy.aidl

import android.app.Service
import android.content.Intent

class AidlService : Service() {
    // BookManager.Stub 继承了Binder，直接使用BookManagerStub的单例返回IBinder对象
    override fun onBind(intent: Intent?) = BookManagerStub.Instance
}