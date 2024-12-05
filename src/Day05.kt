import kotlin.time.TimeSource

fun main() {

    fun Int.validateAgainst(remainingItems: List<Int>, lookupTable: Map<Int, List<Int>>): Boolean {
        if (lookupTable[this] == null) {
            return false
        }
        val validItems = lookupTable[this]!!
        return remainingItems.all { validItems.contains(it) }
    }

    fun part1(input: List<String>): Int {
        val lookupTable = mutableMapOf<Int, MutableList<Int>>()
        val dividerIndex = input.indexOf("")
        input.subList(0, dividerIndex).forEach { line ->
            check(line.contains('|')) { "Invalid input: $line" }
            val (first, second) = line.split('|').map { it.toInt() }
            if (lookupTable[first] == null) {
                lookupTable[first] = mutableListOf()
            }
            lookupTable[first]!!.add(second)
        }

        var runningTotal = 0

        input.subList(dividerIndex + 1, input.size).forEach { updateLine ->
            val updateOrder = updateLine.split(',').map { it.toInt() }
            var isValid = true

            updateOrder.forEachIndexed orderList@{ index, value ->
                val remainingItems = updateOrder.subList(index + 1, updateOrder.size)
                if (remainingItems.isEmpty()) {
                    return@orderList
                }
                if(isValid) {
                    isValid = value.validateAgainst(remainingItems, lookupTable)
                }
            }
            if (isValid) {
                val middleNumber = updateOrder[updateOrder.size / 2]
                println(updateOrder)
                println(middleNumber)
                runningTotal += middleNumber
            }
        }
        return runningTotal
    }

    fun List<Int>.reorder(lookupMap: Map<Int, List<Int>>): List<Int> {
        val copy = this.toMutableList()
        val rankingMap = mutableMapOf<Int, Int>()
        copy.forEach {value ->
            val containList = lookupMap[value] ?: emptyList()
            val rank = containList.intersect(copy).size
            rankingMap[value] = rank
        }
        return rankingMap.toList().sortedBy { -it.second }.map { it.first }
    }

    fun part2(input: List<String>): Int {
        val lookupTable = mutableMapOf<Int, MutableList<Int>>()
        val dividerIndex = input.indexOf("")
        input.subList(0, dividerIndex).forEach { line ->
            check(line.contains('|')) { "Invalid input: $line" }
            val (first, second) = line.split('|').map { it.toInt() }
            if (lookupTable[first] == null) {
                lookupTable[first] = mutableListOf()
            }
            lookupTable[first]!!.add(second)
        }

        var runningTotal = 0

        val timeSource =TimeSource.Monotonic
        val mark1 = timeSource.markNow()
        input.subList(dividerIndex + 1, input.size).forEach { updateLine ->
            val updateOrder = updateLine.split(',').map { it.toInt() }
            var isValid = true
            var updatedOrder = updateOrder
            var index = 0
            while (index < updateOrder.size && isValid) {
                var value = updatedOrder[index]
                val remainingItems = updatedOrder.subList(index + 1, updatedOrder.size)
                if (remainingItems.isNotEmpty()) {
                    val validNumber = value.validateAgainst(remainingItems, lookupTable)
                    if(validNumber) {
                        index++
                    } else {
                        isValid = false
                        updatedOrder = updatedOrder.reorder(lookupTable)
                    }
                } else {
                    index++
                }
            }
            if (!isValid) {
                val middleNumber = updatedOrder[updatedOrder.size / 2]
                runningTotal += middleNumber
            }
        }
        val mark2 = timeSource.markNow()
        val duration = mark2 - mark1
        println(duration)
        println(runningTotal)
        return runningTotal
    }


    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day05_test")
//    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day05")
//    part1(input).println()
    part2(input).println()
}
