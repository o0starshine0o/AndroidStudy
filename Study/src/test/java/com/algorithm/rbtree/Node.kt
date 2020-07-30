package com.algorithm.rbtree

import androidx.annotation.IntDef

const val Black = 0
const val Red = 1

@IntDef(Black, Red)
@Retention(AnnotationRetention.SOURCE)
annotation class Color

/**
 * 学习红黑树
 * 使用泛型增加通用性
 */
class Node<K : Comparable<K>, V>(var key: K, var value: V) {
    /**
     * 默认新增的节点红色
     */
    @Color
    var color = Red
    var parent: Node<K, V>? = null
    var left: Node<K, V>? = null
    var right: Node<K, V>? = null

    override fun toString() = "${if (color == Red) "red" else "black"}: $value"
}