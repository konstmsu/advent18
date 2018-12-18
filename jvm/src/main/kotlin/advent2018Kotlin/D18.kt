package advent2018Kotlin

import java.util.stream.Collectors

class D18(lines: List<String>) {
    var step = 0

    fun evolve() {
        fun getAdjacent(x: Int, y: Int): MutableMap<Char, Int> {
            val blocks = mutableMapOf('.' to 0, '|' to 0, '#' to 0)

            fun lookAt(dx: Int, dy: Int) {
                val px = x + dx
                val py = y + dy
                if (px >= 0 && px < lines[0].length && py >= 0 && py < lines.size) {
                    val c = lines[py][px]
                    blocks[c] = blocks[c]!! + 1
                };
            }

            lookAt(-1, -1)
            lookAt(0, -1)
            lookAt(1, -1)

            lookAt(-1, 0)
            lookAt(1, 0)

            lookAt(-1, 1)
            lookAt(0, 1)
            lookAt(1, 1)

            return blocks
        }

        val next = lines.map(::StringBuilder)

        for (y in 0 until lines.size) {
            for (x in 0 until lines[0].length) {
                val adj = getAdjacent(x, y)
                val c = lines[y][x]
                if (c == '.' && adj['|']!! >= 3)
                    next[y][x] = '|'
                if (c == '|' && adj['#']!! >= 3)
                    next[y][x] = '#'
                if (c == '#') {
                    if (adj['#']!! == 0 || adj['|']!! == 0) {
                        next[y][x] = '.'
                    }
                }
            }
        }

        // . open
        // | tree
        // # lumberyard

        this.lines = next.map { it.toString() }
        step++;
    }

    fun render(): String {
        return lines.joinToString(System.lineSeparator())
    }

    var lines = lines.filter { !it.isNullOrEmpty() }
    val solution1: Int
        get() {
            while (step < 10)
                evolve()

            return getResourceValue()
        }

    val solution2: Int
        get() {
            val period = 28
            var time = 1_000_000_000

            while (step < time - (time / period - 20) * period) {
                evolve()
                System.out.println(getResourceValue())
            }

            return getResourceValue()
        }

    private fun getResourceValue(): Int {
        val s = lines.joinToString("")
        return s.count { it == '|' } * s.count { it == '#' }
    }

    companion object {
        fun parse(input: String): D18 {
            return D18(input.lineSequence().toList())
        }
    }
}