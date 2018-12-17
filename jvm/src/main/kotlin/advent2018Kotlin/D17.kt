package advent2018Kotlin

import kotlin.math.max
import kotlin.math.min

class D17(val groundMap: GroundMap) {
    fun render() = groundMap.lines.joinToString(System.lineSeparator())

    data class Point(val x: Int, val y: Int)

    class GroundMap(val x: IntRange, val y: IntRange) {
        val lines = y.map { StringBuilder(".".repeat(x.count())) }

        operator fun get(p: Point) = lines[p.y - y.first][p.x - x.first]
        operator fun set(p: Point, c: Char) {
            lines[p.y - y.first][p.x - x.first] = c
        }
    }

    companion object {
        fun parse(input: String): D17 {
            class ClayVein(val x: IntRange, val y: IntRange)

            val clayVeins = input.lineSequence().map { l ->
                val p = Regex("(x|y)=(\\d+), (x|y)=(\\d+)\\.\\.(\\d+)").matchEntire(l)!!.groupValues
                val n = p.slice(listOf(2, 4, 5)).map(Integer::parseInt)
                val bounds = HashMap<String, IntRange>()
                bounds[p[1]] = n[0]..n[0]
                bounds[p[3]] = n[1]..n[2]
                ClayVein(bounds["x"]!!, bounds["y"]!!)
            }.toList()

            val spring = Point(500, 0)

            fun IntRange.with(v: Int) = min(first, v)..max(last, v)

            val xmin = clayVeins.map { it.x.first }.min()!! - 1
            val xmax = clayVeins.map { it.x.last }.max()!! + 1
            val ymin = clayVeins.map { it.y.first }.min()!!
            val ymax = clayVeins.map { it.y.last }.max()!!

            val groundMap = GroundMap((xmin..xmax).with(spring.x), (ymin..ymax).with(spring.y))

            for (b in clayVeins)
                for (y in b.y)
                    for (x in b.x)
                        groundMap[Point(x, y)] = '#'

            groundMap[spring] = '+'

            return D17(groundMap);
        }

    }
}