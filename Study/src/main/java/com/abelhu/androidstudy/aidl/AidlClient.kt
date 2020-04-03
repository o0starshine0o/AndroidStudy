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
        bookManager = BookManager.Stub.asInterface(service)
        // 接下来就是调用服务端的接口
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