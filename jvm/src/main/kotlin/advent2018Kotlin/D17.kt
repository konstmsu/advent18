package advent2018Kotlin

import java.lang.IllegalArgumentException
import java.util.*
import kotlin.math.max
import kotlin.math.min

class D17(val groundMap: GroundMap) {
    val minClayY
        get() = groundMap.ground.filterValues { it == Filling.Clay }.keys.map { it.y }.min()!!

    val solution1
        get() = groundMap.ground.count { it.value in setOf(D17.Filling.Flow, D17.Filling.Still) } - minClayY

    val solution2
        get() = groundMap.ground.count { it.value == Filling.Still }

    fun render() = groundMap.render()

    fun spill() {
        groundMap.spill()
    }

    data class Point(val x: Int, val y: Int) {
        fun offset(dx: Int, dy: Int) = Point(x + dx, y + dy)
    }

    enum class Filling(val char: Char) {
        Clay('#'),
        Flow('|'),
        Still('~')
    }

    class GroundMap(private val spring: Point, clayX: IntRange, clayY: IntRange) {
        private fun IntRange.with(v: Int) = min(first, v)..max(last, v)

        val x = clayX.with(spring.x)
        val y = clayY.with(spring.y)

        val ground = mutableMapOf(spring to Filling.Flow)

        fun render(): String {
            val lines = y.map { StringBuilder(".".repeat(x.count())) }
            fun set(p: Point, c: Char) {
                lines[p.y - y.first][p.x - x.first] = c
            }

            for (p in ground)
                set(p.key, p.value.char)

            set(spring, '+')

            return lines.joinToString(System.lineSeparator())
        }

        fun spill() {
            while (true) {
                val toConsider = ArrayDeque<Point>(ground.filterValues { it == Filling.Flow }.keys)
                var hasChanges = false

                //print()
                while (!toConsider.isEmpty()) {
                    var p = toConsider.removeFirst()!!
                    val left = p.offset(-1, 0)
                    val right = p.offset(1, 0)
                    val down = p.offset(0, 1)
                    if (ground[left] == Filling.Clay && ground[down] in listOf(Filling.Clay, Filling.Still)) {
                        var dx = 0
                        var shouldBeStill = false
                        while (true) {
                            dx++
                            val cp = p.offset(dx, 0)
                            if (ground[cp.offset(0, 1)] !in listOf(Filling.Clay, Filling.Still)) {
                                break
                            }
                            if (ground[cp] == Filling.Clay) {
                                shouldBeStill = true
                                break
                            }
                        }
                        if (shouldBeStill) {
                            for(i in 0 until dx) {
                                ground[p.offset(i, 0)] = Filling.Still
                                val up = p.offset(i, -1)
                                if (ground[up] == Filling.Flow)
                                    toConsider.addFirst(up)
                            }
                        }
                    }

                    fun flow(p: Point) {
                        if (ground[p] != null)
                            return

                        ground[p] = Filling.Flow
                        toConsider.addFirst(p)
                        hasChanges = true
                    }

                    if (down.y <= y.last)
                        flow(down)

                    if (ground[down] in listOf(Filling.Clay, Filling.Still)) {
                        flow(left)
                        flow(right)
                    }
                }

                if (hasChanges)
                    break;
            }
        }


        fun print() {
            System.out.println(render())
            System.out.println()
            System.out.println()
        }
    }

    companion object {
        fun parse(input: String): D17 {
            class ClayVein(val x: IntRange, val y: IntRange)

            val clayVeins = input.lineSequence().filter { !it.isNullOrEmpty() }.map { l ->
                val p = Regex("(x|y)=(\\d+), (x|y)=(\\d+)\\.\\.(\\d+)").matchEntire(l)!!.groupValues
                val n = p.slice(listOf(2, 4, 5)).map(Integer::parseInt)
                val bounds = HashMap<String, IntRange>()
                bounds[p[1]] = n[0]..n[0]
                bounds[p[3]] = n[1]..n[2]
                ClayVein(bounds["x"]!!, bounds["y"]!!)
            }.toList()

            val spring = Point(500, 0)

            val xmin = clayVeins.map { it.x.first }.min()!! - 1
            val xmax = clayVeins.map { it.x.last }.max()!! + 1
            val ymin = clayVeins.map { it.y.first }.min()!!
            val ymax = clayVeins.map { it.y.last }.max()!!

            val groundMap = GroundMap(spring, xmin..xmax, ymin..ymax)

            for (b in clayVeins)
                for (y in b.y)
                    for (x in b.x)
                        groundMap.ground[Point(x, y)] = Filling.Clay

            return D17(groundMap);
        }

        fun parseMap(text: String): D17 {
            val lines = text.lineSequence().toList()
            val springOffset = lines[0].indexOf('+')

            val xmin = 500 - springOffset;
            val xmax = xmin + lines.map { it.length }.distinct().single() - 1
            val ymin = 0
            val ymax = lines.size - 1

            val groundMap = GroundMap(Point(500, 0), xmin..xmax, ymin..ymax)

            for (y in 0 until lines.size) {
                val l = lines[y]
                for (x in 0 until l.length) {
                    val f = Point(x + xmin, y + ymin)
                    when (l[x]) {
                        '#' -> groundMap.ground[f] = Filling.Clay
                        '.' -> {
                        }
                        '+' -> {
                        }
                        else -> throw IllegalArgumentException()
                    }
                }
            }

            return D17(groundMap)
        }

    }
}