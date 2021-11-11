package com.tencent.qqsports.feed.wrapper

import org.junit.Assert.assertTrue
import org.junit.Test

class TDD {

    enum class Direction { North, South, West, East }
    enum class FB { Forward, Backward }
    enum class Round { Left, Right }
    interface Action
    data class Area(val w: Int, val h: Int) : Action
    data class Location(val x: Int, val y: Int, val direction: Direction) : Action {
        override fun equals(other: Any?): Boolean {
            if (other is Car) return other.x == x && other.y == y && other.direction == direction
            return false
        }
    }

    data class Move(val fb: FB) : Action
    data class Turn(val turn: Round) : Action

    class Car {
        private var init = false
        private var land = false
        private var width = 0
        private var height = 0
        var x = 0
        var y = 0
        var direction = Direction.North

        fun run(actions: List<Action>): Car {
            actions.forEach {
                when (it) {
                    is Area -> run(it)
                    is Location -> run(it)
                    is Move -> run(it)
                    is Turn -> run(it)
                }
            }
            return this
        }

        private fun run(action: Area) {
            if (init) throw Exception()
            init = true
            width = action.w
            height = action.h
        }

        private fun run(action: Location) {
            if (!init && land) throw Exception()

            land = true
            x = action.x
            y = action.y
            direction = action.direction
        }

        private fun run(action: Move) {
            if (!init || !land) throw Exception()

            when (action.fb) {
                FB.Forward -> run(direction, 1)
                FB.Backward -> run(direction, -1)
            }
        }

        private fun run(direction: Direction, step: Int) {
            if (!init || !land) throw Exception()

            when (direction) {
                Direction.North -> y -= step
                Direction.South -> y += step
                Direction.West -> x -= step
                Direction.East -> x += step
            }

            if (x > width) throw Exception()
            if (x < 0) throw Exception()
            if (y < 0) throw Exception()
            if (y > height) throw Exception()
        }

        private fun run(turn: Turn) {
            if (!init || !land) throw Exception()

            direction = when (turn.turn) {
                Round.Left -> when (direction) {
                    Direction.North -> Direction.West
                    Direction.West -> Direction.South
                    Direction.South -> Direction.East
                    Direction.East -> Direction.North
                }
                Round.Right -> when (direction) {
                    Direction.North -> Direction.East
                    Direction.East -> Direction.South
                    Direction.South -> Direction.West
                    Direction.West -> Direction.North
                }
            }
        }
    }

    @Test
    fun testMove() {
        // 各种指令
        val area = Area(100, 100)
        val init = Location(50, 50, Direction.East)
        val move = Move(FB.Forward)
        val turn = Turn(Round.Left)
        // 测试对象
        val car = Car()
        // 验证对象
        val location = Location(51, 50, Direction.North)
        // 输入参数
        val actions = listOf(area, init, move, turn)
        // 验证结果
        assertTrue(location.equals(car.run(actions)))
    }
}