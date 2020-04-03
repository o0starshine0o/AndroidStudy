package com.abelhu.androidstudy.binder.client

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.os.IBinder.FLAG_ONEWAY
import android.os.Parcel

class MyClient : ServiceConnection {
    override fun onServiceDisconnected(name: ComponentName?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        // Client进程 将需要传送的数据写入到Parcel对象中
        val data = Parcel.obtain()
        data.writeInt(1)
        data.writeInt(2)
        // 让Server进程在Binder对象中根据interfaceName通过queryLocalIInterface（）查找相应的IInterface对象
        data.writeInterfaceToken("19920320")
        // 目标方法执行后的结果
        val reply = Parcel.obtain()
        // 使用服务端的IBinder，执行对应的方法并获取返回值
        // 使用FLAG_ONE_WAY表示等待服务端返回数据
        service?.transact(19920320, data, reply, FLAG_ONEWAY)
        // 获取服务端的返回值
        val result = reply.readInt()
        // 回收Parcel
        data.recycle()
        reply.recycle()
    }
}