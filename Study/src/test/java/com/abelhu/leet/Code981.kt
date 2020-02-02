package com.abelhu.leet

import org.junit.Test
import java.util.*
import kotlin.collections.HashMap

class Code981 {
    class TimeMap {
        private val map = HashMap<String, ArrayList<Pair<Int, String>>>()

        fun set(key: String, value: String, timestamp: Int) {
            if (!map.containsKey(key)) map[key] = ArrayList()
            map[key]?.add(Pair(timestamp, value))
        }

        fun get(key: String, timestamp: Int): String {
            val list = map.getOrDefault(key, ArrayList())
            return when (val i = Collections.binarySearch(list, Pair(timestamp, "")) { a, b -> a.first.compareTo(b.first) }) {
                in 0..Int.MAX_VALUE -> list[i].second
                -1 -> ""
                else -> list[-i - 2].second
            }
        }

    }

    @Test
    fun test0() {
        val map = TimeMap()
        map.set("foo", "bar", 1)
        assert("bar" == map.get("foo", 1))
        assert("bar" == map.get("foo", 3))
        map.set("foo", "bar2", 4)
        assert("bar2" == map.get("foo", 4))
        assert("bar2" == map.get("foo", 5))
    }

    @Test
    fun test1() {
        val map = TimeMap()
        map.set("love", "high", 10)
        map.set("love", "low", 20)
        assert("" == map.get("love", 5))
        assert("high" == map.get("love", 10))
        assert("high" == map.get("love", 15))
        assert("low" == map.get("love", 20))
        assert("low" == map.get("love", 25))
    }
}

