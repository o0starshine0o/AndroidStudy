package com.abelhu.huawei

import java.util.*

fun main(args: Array<String>) {
    val read = Scanner(System.`in`)


    val m = read.nextInt()
    val n = read.nextInt()

    val children = MutableList(0) { 0 }
    val attack = IntArray(n) { 0 }
    val protect = IntArray(n) { 0 }

    for (i in 0 until m) children.add(read.nextInt())

    for (i in 0 until n) {
        attack[i] = read.nextInt()
        protect[i] = read.nextInt()
    }

    play(m, n, children, attack, protect)

    print(children)

}

fun play(m: Int, n: Int, children: MutableList<Int>, attack: IntArray, protect: IntArray) {
    var round = 0
    while (round < n && children.size > 1) {
        if (attack[round]-1 < children.size && attack[round] != protect[round]) children.removeAt(attack[round]-1)
        round++
    }
}

fun print(children: MutableList<Int>) {
    if (children.size > 1) {
        children.sort()
        print("Success!")
        for (child in children) print(" $child")
    } else {
        print("Fail! ${children.first()}")
    }
}
