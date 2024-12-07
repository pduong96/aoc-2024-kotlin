fun main() {
    fun List<Long>.produceAllPossibleResult(): List<Long> {
        val resultQueue = mutableListOf<Long>()
        this.forEachIndexed { index, number ->
            if (resultQueue.isEmpty()) {
                resultQueue.add(number)
            } else {
                for (i in 0 until resultQueue.size) {
                    val toOperate = resultQueue.removeFirst()
                    resultQueue.add(toOperate + number)
                    resultQueue.add(toOperate * number)

                }
            }
        }
        return resultQueue
    }

    fun List<Long>.produceAllPossibleResultWithExtra(): List<Long> {
        val resultQueue = mutableListOf<Long>()
        this.forEachIndexed { index, number ->
            if (resultQueue.isEmpty()) {
                resultQueue.add(number)
            } else {
                for (i in 0 until resultQueue.size) {
                    val toOperate = resultQueue.removeFirst()
                    resultQueue.add(toOperate + number)
                    resultQueue.add(toOperate * number)
                    resultQueue.add("$toOperate$number".toLong())
                }
            }
        }
        return resultQueue
    }

    fun part1(input: List<String>): Long {
        var runningTotal = 0L
        input.forEach { line ->
            val (resultString, equation) = line.split(":")
            val numbers = equation.split(" ").mapNotNull { it.toLongOrNull() }
            val result = resultString.toLong()

            val allPossibleResults = numbers.produceAllPossibleResult()
            if (allPossibleResults.contains(result)) {
                runningTotal += result
            }
        }


        //7867712552551
        //7885693428401
//        println(runningTotal)
        return runningTotal
    }


    fun part2(input: List<String>): Long {
        var runningTotal = 0L
        input.forEach { line ->
            val (resultString, equation) = line.split(":")
            val numbers = equation.split(" ").mapNotNull { it.toLongOrNull() }
            val result = resultString.toLong()

            val allPossibleResults = numbers.produceAllPossibleResultWithExtra()
            if (allPossibleResults.contains(result)) {
                runningTotal += result
            }
        }

        return runningTotal
    }


    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day07_test")
//    check(part1(testInput) == 3749L)
//    check(part2(testInput) == 11387L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day07")
    part1(input).println()
//    part2(input).println()
}
