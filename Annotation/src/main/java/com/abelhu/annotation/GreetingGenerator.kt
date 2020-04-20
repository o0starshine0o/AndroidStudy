package com.abelhu.annotation

/**
 * 允许在单个元素上多次使用同一个注解
 */
//@Repeatable
/**
 * MustBeDocumented 它的作用是能够将注解中的元素包含到 Javadoc 中去。
 */
@MustBeDocumented
/**
 * Retention 的英文意为保留期的意思。当 @Retention 应用到一个注解上的时候，它解释说明了这个注解的的存活时间
 * AnnotationRetention.SOURCE 注解只在源码阶段保留，在编译器进行编译时它将被丢弃忽视。
 * AnnotationRetention.CLASS 注解只被保留到编译进行的时候，它并不会被加载到 JVM 中。
 * AnnotationRetention.RUNTIME 注解可以保留到程序运行的时候，它会被加载进入到 JVM 中，所以在程序运行时可以获取到它们。
 */
@Retention(AnnotationRetention.SOURCE)
/**
 * 指定了注解运用的地方
 */
@Target(AnnotationTarget.CLASS)
annotation class GreetingGenerator