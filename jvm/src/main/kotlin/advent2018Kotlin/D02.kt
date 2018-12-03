package advent2018Kotlin

class D02 {
    companion object {
        fun input2(lines: List<String>): String {
            return (0 until lines[0].length).flatMap { i ->
                lines
                        .map { l -> l.replaceRange(i..i, "") }
                        .groupBy { it -> it }
                        .filter { it -> it.component2().count() > 1 }
                        .keys
            }.first()
        }
    }
}