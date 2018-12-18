package advent2018Kotlin.test

import advent2018.Utils
import advent2018Kotlin.D18
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.nio.file.Files

class D18Tests {
    @Test
    fun sample1() {
        var game = D18.parse("""
            .#.#...|#.
            .....#|##|
            .|..|...#.
            ..|#.....#
            #.#|||#|#|
            ...#.||...
            .|....|...
            ||...#|.#|
            |.||||..|.
            ...#.|..|.
        """.trimIndent().trim())

        game.evolve()

        assertThat(game.render()).isEqualTo("""
            .......##.
            ......|###
            .|..|...#.
            ..|#||...#
            ..##||.|#|
            ...#||||..
            ||...|||..
            |||||.||.|
            ||||||||||
            ....||..|.
        """.trimIndent().trim())

        assertThat(game.solution1).isEqualTo(1147)
    }

    @Test
    fun input1() {
        var game = D18.parse(Files.readString(Utils.of("d18.txt")))
        assertThat(game.solution1).isEqualTo(519552)
    }

    @Test
    fun input2() {
        var game = D18.parse(Files.readString(Utils.of("d18.txt")))
        assertThat(game.solution2).isEqualTo(165376)
    }
}