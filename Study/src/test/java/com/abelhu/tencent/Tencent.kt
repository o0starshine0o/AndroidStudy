package com.abelhu.tencent

import org.junit.Assert
import org.junit.Test

class Tencent {

    fun revert(input: String): String {
        // 边界
        val words = input.split(" ")
        val size = words.size
        if (size <= 1) return input

        val result = StringBuffer()
        for (i in (size - 1) downTo 0) {
            result.append(words[i])
            if (i > 0) result.append(" ")
        }
        return result.toString()

    }


    @Test
    fun test0(){
        Assert.assertEquals("World Hello", revert("Hello World"))
    }

}