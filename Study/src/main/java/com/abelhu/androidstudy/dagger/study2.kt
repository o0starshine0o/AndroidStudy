package com.abelhu.androidstudy.dagger

import dagger.Component
import javax.inject.Inject

// https://blog.yorek.xyz/android/other/dagger2/
fun main() {

}

/**
 * 在Dog类的无参构造函数上加一个@Inject注解
 */
class Dog @Inject constructor() {
    fun drink() = "drink"
}

/**
 * 假设这是个Activity
 */
class DaggerActivity {

    @Inject
    lateinit var dog: Dog

    init {
    }
}

@Component
interface DogComponent {
    fun createDog(): Dog
    fun inject(activity: DaggerActivity)
}