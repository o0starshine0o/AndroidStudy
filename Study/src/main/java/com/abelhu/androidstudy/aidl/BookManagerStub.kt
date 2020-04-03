package com.abelhu.androidstudy.aidl

import com.abelhu.androidstudy.Book
import com.abelhu.androidstudy.BookManager

class BookManagerStub private constructor() : BookManager.Stub() {
    companion object {
        // 利用lazy实现的双重检测单例
        val Instance: BookManagerStub by lazy { BookManagerStub() }
    }

    //包含Book对象的list
    private val books = MutableList(0) { Book() }

    override fun addBookInout(book: Book?) {
        book?.also { books.add(it) }
    }

    override fun setBookName(book: Book?, name: String?) {
        book?.name = name
    }

    override fun addBookOut(book: Book?) {
        book?.also { books.add(it) }
    }

    override fun getBookCount() = books.size

    override fun setBookPrice(book: Book?, price: Int) {
        book?.price = price
    }

    override fun addBookIn(book: Book?) {
        book?.also { books.add(it) }
    }

    override fun getBooks() = books

    override fun getBook(index: Int) = books.getOrNull(index)

}