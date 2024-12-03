import kotlin.math.abs

fun String.findAllIndexesOfMatches(toMatch: String): List<Int> {
    val indexes = mutableListOf<Int>()
    var index = indexOf(toMatch)
    while (index >= 0) {
        indexes.add(index)
        index = indexOf(toMatch, index + 1)
    }
    return indexes
}


fun main() {
    fun part1(input: List<String>): Int {
        var runningTotal = 0
        input.forEach { line ->
            val mulIndexes = line.findAllIndexesOfMatches("mul(")
            mulIndexes.forEach { index ->
                val formula = line.substring(index, line.indexOf(")", index) + 1)
                val parameters = formula.substring(4, formula.length - 1).replace(" ", "_").split(",")
                val isValid = parameters.all { it.toIntOrNull() != null && it.count() <= 3 } && parameters.count() == 2
                if(isValid) {
                    check(formula == "mul(${parameters.first()},${parameters.last()})") { "Invalid formula: $formula" }
                    runningTotal += parameters.first().toInt() * parameters.last().toInt()
                }
            }
        }

        return runningTotal
    }



    fun part2(input: List<String>): Int {
        var runningTotal = 0
        var isDo = true

        input.forEach { line ->
            val mulIndexes = line.findAllIndexesOfMatches("mul(").map { Pair(it, "mul(")}
            val doIndexes = line.findAllIndexesOfMatches("do()").map { Pair(it, "do()")}
            val dontIndexes = line.findAllIndexesOfMatches("don't()").map { Pair(it, "don't()")}

            val combinedIndexes = (mulIndexes + doIndexes + dontIndexes).sortedBy { it.first }
            combinedIndexes.forEach processing@{ (index, type) ->
                when(type) {
                    "do()" -> isDo = true
                    "don't()" -> isDo = false
                    "mul(" -> {
                        if(!isDo){
                            return@processing
                        }
                        val formula = line.substring(index, line.indexOf(")", index) + 1)
                        val parameters = formula.substring(4, formula.length - 1).replace(" ", "_").split(",")
                        val isValid = parameters.all { it.toIntOrNull() != null && it.count() <= 3 } && parameters.count() == 2
                        if(isValid) {
                            check(formula == "mul(${parameters.first()},${parameters.last()})") { "Invalid formula: $formula" }
                            runningTotal += parameters.first().toInt() * parameters.last().toInt()
                        }
                    }
                }
            }
        }

        return runningTotal
    }


    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day03_test")
//    check(part1(testInput) == 161)
    check(part2(testInput) == 48)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
//    part1(input).println()
    part2(input).println()
}
