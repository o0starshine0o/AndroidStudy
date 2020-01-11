package com.abelhu.androidstudy

import com.abelhu.androidstudy.application.Generated_Greet
import com.abelhu.annotation.GreetingGenerator
import org.junit.Test

class Annotation {
    @Test
    fun testGreeting(){
        assert("Merry Christmas!!" == Generated_Greet().greeting())
    }

    @GreetingGenerator
    class Greet
}