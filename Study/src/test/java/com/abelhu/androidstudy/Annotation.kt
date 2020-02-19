package com.abelhu.androidstudy

import com.abelhu.annotation.GreetingGenerator
import org.junit.Test

class Annotation {
    @Test
    fun testGreeting(){
//        assert("Merry Christmas!!" == Generated_Greet().greeting())
    }

    @GreetingGenerator
    class Greet
}