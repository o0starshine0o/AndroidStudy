package com.abelhu.androidstudy.annotation

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class ShowAnnotation(val value:String="empty")