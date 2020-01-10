package com.abelhu.leet

import org.junit.Test

class Code1169 {
    private fun invalidTransactions(transactions: Array<String>): List<String> {
        val result = MutableList(0) { "" }
        // 根据条件进行判断
        for ((i, transaction) in transactions.withIndex()) {
            val action = transaction.split(",")
            // 格式不正确
            if (action.size != 4) {
                result.add(transaction)
                continue
            }
            // 交易数量超过1000
            if (action[2].toInt() > 1000) {
                result.add(transaction)
                continue
            }
            // 时间在60之内
            for ((j, transaction2) in transactions.withIndex()) {
                if (i == j) continue
                val action2 = transaction2.split(",")
                if (action[0] == action2[0] && kotlin.math.abs(action[1].toInt() - action2[1].toInt()) <= 60 && action[3] != action2[3]) {
                    result.add(transaction)
                    break
                }
            }
        }
        return result
    }

    @Test
    fun test0() {
        val transactions = arrayOf("alice,20,800,mtv", "alice,50,100,beijing")
        val expect = listOf("alice,20,800,mtv", "alice,50,100,beijing")
        val result = invalidTransactions(transactions)
        assert(expect.containsAll(result))
        assert(result.containsAll(expect))
    }

    @Test
    fun test1() {
        val transactions = arrayOf("alice,20,800,mtv", "alice,50,1200,mtv")
        val expect = listOf("alice,50,1200,mtv")
        val result = invalidTransactions(transactions)
        assert(expect.containsAll(result))
        assert(result.containsAll(expect))
    }

    @Test
    fun test2() {
        val transactions = arrayOf("alice,20,800,mtv", "bob,50,1200,mtv")
        val expect = listOf("bob,50,1200,mtv")
        val result = invalidTransactions(transactions)
        assert(expect.containsAll(result))
        assert(result.containsAll(expect))
    }
}