package com.abelhu.androidstudy.aidl

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import com.abelhu.androidstudy.Book
import com.abelhu.androidstudy.BookManager

class AidlClient private constructor() : ServiceConnection {
    companion object {
        val Instance: AidlClient by lazy { AidlClient() }
    }

    private lateinit var bookManager: BookManager

    override fun onServiceDisconnected(name: ComponentName?) {
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        // 这个service就是服务端的Service了，通过向ServiceManager查询的方式，启动了位于服务端的Service
        // 这个BookManager类是直接从服务端拷贝而来的，只能保证调用其中的方法而已，不能作为一个单独的"实例"存在
        bookManager = BookManager.Stub.asInterface(service)
        // 接下来就是调用服务端的接口
        // 客户端以为是直接调用接口，其实是被转换成了code，最后交给onTransact方法去switch
        bookManager.books
        bookManager.getBook(0)
        bookManager.bookCount
        bookManager.addBookIn(Book("Test", 100))
        bookManager.addBookInout(Book("Test", 100))
        bookManager.addBookOut(Book("Test", 100))
        bookManager.setBookName(Book("Test", 100), "Name")
        bookManager.setBookPrice(Book("Test", 100), 200)
    }
}