package com.abelhu.androidstudy.hilt.annotation

import javax.inject.Qualifier

/**
 * 为adapter提供多种绑定
 */

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApplicationAdapter

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ActivityAdapter

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FragmentAdapter

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ViewAdapter