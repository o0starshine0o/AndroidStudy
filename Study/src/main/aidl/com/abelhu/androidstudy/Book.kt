package com.abelhu.androidstudy

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by admin on 2020-04-03
 */
data class Book(var name: String? = null, var price: Int = 0) : Parcelable {
    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        price = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(price)
    }

    /**
     * 参数是一个Parcel,用它来存储与传输数据
     * @param book 注意，此处的读值顺序应当是和writeToParcel()方法中一致的
     */
    fun readFromParcel(book: Parcel) {
        name = book.readString()
        price = book.readInt()
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }
}