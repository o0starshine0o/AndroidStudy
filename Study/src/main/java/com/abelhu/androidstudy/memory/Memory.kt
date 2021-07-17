package com.abelhu.androidstudy.memory

import android.os.Build
import android.os.MemoryFile
import android.os.SharedMemory
import androidx.annotation.RequiresApi
import java.io.FileInputStream

class Memory {
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    fun test(){
        val memoryFile = MemoryFile("memory", 1000)
        val sharedMemory = SharedMemory.create("memory", 1000)
        val input = FileInputStream("descriptor")
    }
}