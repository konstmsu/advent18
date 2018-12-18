package advent2018Kotlin.test

import advent2018.Utils
import advent2018Kotlin.D17
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.nio.file.Files

class D17Tests {
    @Test
    fun shouldParse() {
        assertThat(D17.parse("""
                x=495, y=2..7
                y=7, x=495..501
                x=501, y=3..7
                x=498, y=2..4
                x=506, y=1..2
                x=498, y=10..13
                x=504, y=10..13
                y=13, x=498..504
                """.trimIndent().trim()
        ).render()).isEqualTo("""
                ......+.......
                ............#.
                .#..#.......#.
                .#..#..#......
                .#..#..#......
                .#.....#......
                .#.....#......
                .#######......
                ..............
                ..............
                ....#.....#...
                ....#.....#...
                ....#.....#...
                ....#######...
                """.trimIndent().trim())
    }

    @Test
    fun spill() {
        val game = D17.parse("""
                x=495, y=2..7
                y=7, x=495..501
                x=501, y=3..7
                x=498, y=2..4
                x=506, y=1..2
                x=498, y=10..13
                x=504, y=10..13
                y=13, x=498..504
                """.trimIndent().trim())

        game.spill();

        assertThat(game.render()).isEqualTo("""
                ......+.......
                ......|.....#.
                .#..#||||...#.
                .#..#~~#|.....
                .#..#~~#|.....
                .#~~~~~#|.....
                .#~~~~~#|.....
                .#######|.....
                ........|.....
                ...|||||||||..
                ...|#~~~~~#|..
                ...|#~~~~~#|..
                ...|#~~~~~#|..
                ...|#######|..
                """.trimIndent().trim())

        assertThat(game.solution1).isEqualTo(57)
        assertThat(game.solution2).isEqualTo(29)
    }

    @Test
    fun spill3() {
        val game = D17.parseMap("""
                ......+.......
                ....#....#....
                ....#..#......
                ....####......
                ......#...#...
                ........#.....
                ..............
                ....#.....#...
                ....#.....#...
                ....#######...
                """.trimIndent().trim())

        game.spill();

        assertThat(game.render()).isEqualTo("""
                ......+.......
                ....#||||#....
                ....#~~#|.....
                ....####|.....
                ......#|||#...
                .......|#|....
                ...|||||||||..
                ...|#~~~~~#|..
                ...|#~~~~~#|..
                ...|#######|..
                """.trimIndent().trim())
    }


    @Test
    fun spill4() {
        val game = D17.parseMap("""
                ...+.....
                .#....#..
                .####.#..
                .#.....#.
                .#.#####.
                """.trimIndent().trim())

        game.spill();

        assertThat(game.render()).isEqualTo("""
                ...+.....
                .#||||#..
                .####|#..
                .#|||||#.
                .#|#####.
                """.trimIndent().trim())
    }


    @Test
    fun spill2() {
        val game = D17.parse("""
                x=497, y=2..7
                y=7, x=497..503
                x=503, y=3..7
                x=500, y=2..4
                x=506, y=1..2
                x=498, y=10..13
                x=504, y=10..13
                y=13, x=498..504
                """.trimIndent().trim())

        game.spill();

        assertThat(game.render()).isEqualTo("""
                ....+.......
                ||||||....#.
                |#~~#||||.#.
                |#~~#~~#|...
                |#~~#~~#|...
                |#~~~~~#|...
                |#~~~~~#|...
                |#######|...
                |.......|...
                ||||||||||..
                ||#~~~~~#|..
                ||#~~~~~#|..
                ||#~~~~~#|..
                ||#######|..
                """.trimIndent().trim())
    }

    @Test
    fun input1() {
        val game = D17.parse(Files.readString(Utils.of("d17.txt")))

        assertThat(game.groundMap.ground.values.count { it == D17.Filling.Clay }).isEqualTo(19171)

        game.spill();

        val count = game.solution1
        assertThat(count).isGreaterThan(28202)
        assertThat(count).isLessThan(34252)
        assertThat(count).isLessThan(34251)
        assertThat(count).isEqualTo(34244)

        assertThat(game.solution2).isEqualTo(28202)
    }
}