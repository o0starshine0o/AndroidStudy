package com.abelhu.androidstudy.extension

inline fun <reified T> T.tag(): String = T::class.java.simpleName