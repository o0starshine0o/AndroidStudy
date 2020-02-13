package com.abelhu.leet

import org.junit.Test

class Code1333 {
    fun filterRestaurants(restaurants: Array<IntArray>, veganFriendly: Int, maxPrice: Int, maxDistance: Int): List<Int> {
        // restaurants[i] = [id, rating, veganFriendly, price, distance]
        val availables = MutableList(0) { IntArray(0) }
        // 过滤餐厅
        for (restaurant in restaurants) {
            if (veganFriendly == 1 && restaurant[2] == 0) continue
            else if (restaurant[3] > maxPrice) continue
            else if (restaurant[4] > maxDistance) continue
            else availables.add(restaurant)
        }
        // 排序可用餐厅
        availables.sortWith(compareBy({ it[1] }, { it[0] }))
        // 返回最终列表
        val result = MutableList(0) { 0 }
        for (available in availables) result.add(0, available[0])
        return result
    }

    @Test
    fun test0() {
        val restaurants = arrayOf(
            intArrayOf(1, 4, 1, 40, 10),
            intArrayOf(2, 8, 0, 50, 5),
            intArrayOf(3, 8, 1, 30, 4),
            intArrayOf(4, 10, 0, 10, 3),
            intArrayOf(5, 1, 1, 15, 1)
        )
        assert(listOf(3, 1, 5) == filterRestaurants(restaurants, 1, 50, 10))
    }

    @Test
    fun test1() {
        val restaurants = arrayOf(
            intArrayOf(1, 4, 1, 40, 10),
            intArrayOf(2, 8, 0, 50, 5),
            intArrayOf(3, 8, 1, 30, 4),
            intArrayOf(4, 10, 0, 10, 3),
            intArrayOf(5, 1, 1, 15, 1)
        )
        assert(listOf(4, 3, 2, 1, 5) == filterRestaurants(restaurants, 0, 50, 10))
    }

    @Test
    fun test2() {
        val restaurants = arrayOf(
            intArrayOf(1, 4, 1, 40, 10),
            intArrayOf(2, 8, 0, 50, 5),
            intArrayOf(3, 8, 1, 30, 4),
            intArrayOf(4, 10, 0, 10, 3),
            intArrayOf(5, 1, 1, 15, 1)
        )
        assert(listOf(4, 5) == filterRestaurants(restaurants, 0, 30, 3))
    }
}