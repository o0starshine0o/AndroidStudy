package com.abelhu.androidstudy.dagger

import dagger.Component
import dagger.Lazy
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Qualifier
import javax.inject.Scope

class Man {
    @Inject
    @Type("Blue")
    lateinit var car: Lazy<Car>

    @Inject
    @Type("Red")
    lateinit var cars: Provider<Car>

    init {
        DaggerManComponent.create().injectMan(this)
    }
}

class Car @Inject constructor(val name: String)

@Module
class CarModule {
    @Provides
    @Type("Blue")
    @MyScope
    fun provideBlueCar() = Car("blue car")

    @Provides
    @Type("Red")
    fun provideRedCar() = Car("red car")
}

@MyScope
@Component(modules = [CarModule::class])
interface ManComponent {
    fun injectMan(man: Man)
}

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class Type(val type: String = "default")

@Scope
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class MyScope

fun main() {
    val man = Man()
    for (i in 0..1) {
        val car = man.car.get()
        val cars = man.cars.get()
        println("${car.name}:${car.hashCode()}")
        println("${cars.name}:${cars.hashCode()}")
    }
}