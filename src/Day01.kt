import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val firstList = mutableListOf<Int>()
        val secondList = mutableListOf<Int>()
        var runningTotal = 0

        input.forEach { line ->
            val parts = line.split("   ")
            firstList.add(parts[0].toInt())
            secondList.add(parts[1].toInt())
        }
        firstList.sort()
        secondList.sort()
        firstList.forEachIndexed({ index, value ->
            val difference = abs(value - secondList[index])
            runningTotal += difference
        })
        return runningTotal
    }

    fun part2(input: List<String>): Int {
        val firstList = mutableListOf<Int>()
        val secondList = mutableListOf<Int>()
        var runningTotal = 0

        input.forEach { line ->
            val parts = line.split("   ")
            firstList.add(parts[0].toInt())
            secondList.add(parts[1].toInt())
        }
        val secondListToGroup = secondList.groupBy { it }
        firstList.forEach { value ->
            val numOfTimesInSecondList = secondListToGroup[value]?.size ?: 0
            runningTotal += value * numOfTimesInSecondList
        }
        return runningTotal
    }


    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
//    part1(input).println()
    part2(input).println()
}
