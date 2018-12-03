package advent2018Kotlin.test

import advent2018.Utils
import advent2018Kotlin.D02
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.nio.file.Files

class D02Tests {
    @Test
    fun input2() {
        assertThat(D02.input2(Files.readAllLines(Utils.of("d02.txt"))))
                .isEqualTo("mxhwoglxgeauywfkztndcvjqr");
    }
}