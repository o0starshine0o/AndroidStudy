package com.abelhu.leet

import org.junit.Test

class Code1311 {
    fun watchedVideosByFriends(watchedVideos: List<List<String>>, friends: Array<IntArray>, id: Int, level: Int): List<String> {
        // 先找到id所有level的friends
        val findFriends = mutableSetOf<Int>()
        val finalFriends = MutableList(1) { id }
        val exceptFriends = mutableSetOf<Int>()
        for (i in 0 until level) {
            for (friend in finalFriends) {
                findFriends.addAll(friends[friend].toList())
            }
            exceptFriends.addAll(finalFriends)
            finalFriends.clear()
            finalFriends.addAll(findFriends)
            finalFriends.removeAll(exceptFriends)
            findFriends.clear()
        }
        // 找出所有level的friends的watchVideos
        val map = HashMap<String, Int>()
        for (friend in finalFriends) {
            for (video in watchedVideos[friend]) {
                map[video] = map.getOrDefault(video, 0) + 1
            }
        }
        // 对map进行排序
        return map.toList().sortedWith(compareBy({ it.second }, { it.first })).map { it.first }
    }

    @Test
    fun test0() {
        val watchedVideos = listOf(listOf("A", "B"), listOf("C"), listOf("B", "C"), listOf("D"))
        val friends = arrayOf(intArrayOf(1, 2), intArrayOf(0, 3), intArrayOf(0, 3), intArrayOf(1, 2))
        assert(listOf("B", "C").containsAll(watchedVideosByFriends(watchedVideos, friends, 0, 1)))
    }

    @Test
    fun test1() {
        val watchedVideos = listOf(listOf("A", "B"), listOf("C"), listOf("B", "C"), listOf("D"))
        val friends = arrayOf(intArrayOf(1, 2), intArrayOf(0, 3), intArrayOf(0, 3), intArrayOf(1, 2))
        assert(listOf("D").containsAll(watchedVideosByFriends(watchedVideos, friends, 0, 2)))
    }

    @Test
    fun test2() {
        val watchedVideos = listOf(
            listOf("bjwtssmu"), listOf("aygr", "mqls"), listOf("vrtxa", "zxqzeqy", "nbpl", "qnpl")
            , listOf("r", "otazhu", "rsf"), listOf("bvcca", "ayyihidz", "ljc", "fiq", "viu")
        )
        val friends = arrayOf(intArrayOf(3, 2, 1, 4), intArrayOf(0, 4), intArrayOf(4, 0), intArrayOf(0, 4), intArrayOf(2, 3, 1, 0))
        assert(listOf("ayyihidz", "bjwtssmu", "bvcca", "fiq", "ljc", "viu").containsAll(watchedVideosByFriends(watchedVideos, friends, 3, 1)))
    }
}