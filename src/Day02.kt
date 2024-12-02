import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val validList = input.filter { line ->
            val sequence = line.split(" ")
            val results = sequence.windowed(2).map { it.first().toInt() - it.last().toInt() }
            val allPositive = results.all { it > 0 && abs(it) <= 3}
            val allNegative = results.all { it < 0  && abs(it) <= 3}
            allPositive || allNegative
        }
        return validList.size
    }

    fun testResults(results: List<Int>): Boolean {
        val allPositive = results.all { it > 0 && abs(it) <= 3}
        val allNegative = results.all { it < 0  && abs(it) <= 3}
        return allPositive || allNegative
    }


    fun part2(input: List<String>): Int {
        val validList = input.filter { line ->
            val sequence = line.split(" ").map { it.toInt() }
            val results = sequence.windowed(2).map { it.first() - it.last() }

            val result = testResults(results)
            if (result) {
                return@filter true
            }

            sequence.forEachIndexed { i, currentValue ->
                val newSequence = sequence.filterIndexed { index,_ -> index != i }

                if(testResults(newSequence.windowed(2).map { it.first() - it.last() })) {
                    return@filter true
                }
            }
            false
        }
        return validList.size
    }


    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
//    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
//    part1(input).println()
    part2(input).println()
}
