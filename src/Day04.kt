typealias Pos = Pair<Int, Int>

typealias Line = Pair<Pos, Pos>
//XMAS
//SAMX

fun String.findIndexesOfChar(char: Char): List<Int> {
    val indexes = mutableListOf<Int>()
    this.forEachIndexed { index, c ->
        if (c == char) {
            indexes.add(index)
        }
    }
    return indexes
}

fun Char?.charOrSpace(): Char {
    return this ?: ' '
}

fun main() {

    fun getLines(table: Map<Pos, Char>, currentPos: Pos): List<Line> {
        val validLines = mutableListOf<Line>()
        val leftLine =
            (0..3).map { table[Pos(currentPos.first, currentPos.second - it)].charOrSpace() }.joinToString("")
        if (leftLine == "XMAS") {
            validLines.add(Pair(currentPos, Pos(currentPos.first, currentPos.second - 3)))
        }
        val rightLine =
            (0..3).map { table[Pos(currentPos.first, currentPos.second + it)].charOrSpace() }.joinToString("")
        if (rightLine == "XMAS") {
            validLines.add(Pair(currentPos, Pos(currentPos.first, currentPos.second + 3)))
        }
        val upLine = (0..3).map { table[Pos(currentPos.first - it, currentPos.second)].charOrSpace() }.joinToString("")
        if (upLine == "XMAS") {
            validLines.add(Pair(currentPos, Pos(currentPos.first - 3, currentPos.second)))
        }
        val downLine =
            (0..3).map { table[Pos(currentPos.first + it, currentPos.second)].charOrSpace() }.joinToString("")
        if (downLine == "XMAS") {
            validLines.add(Pair(currentPos, Pos(currentPos.first + 3, currentPos.second)))
        }
        val upLeftLine =
            (0..3).map { table[Pos(currentPos.first - it, currentPos.second - it)].charOrSpace() }.joinToString("")
        if (upLeftLine == "XMAS") {
            validLines.add(Pair(currentPos, Pos(currentPos.first - 3, currentPos.second - 3)))
        }
        val upRightLine =
            (0..3).map { table[Pos(currentPos.first - it, currentPos.second + it)].charOrSpace() }.joinToString("")
        if (upRightLine == "XMAS") {
            validLines.add(Pair(currentPos, Pos(currentPos.first - 3, currentPos.second + 3)))
        }
        val downLeftLine =
            (0..3).map { table[Pos(currentPos.first + it, currentPos.second - it)].charOrSpace() }.joinToString("")
        if (downLeftLine == "XMAS") {
            validLines.add(Pair(currentPos, Pos(currentPos.first + 3, currentPos.second - 3)))
        }
        val downRightLine =
            (0..3).map { table[Pos(currentPos.first + it, currentPos.second + it)].charOrSpace() }.joinToString("")
        if (downRightLine == "XMAS") {
            validLines.add(Pair(currentPos, Pos(currentPos.first + 3, currentPos.second + 3)))
        }
        return validLines
    }

    fun part1(input: List<String>): Int {
        val table = mutableMapOf<Pos, Char>()
        val doneLine = mutableListOf<Line>()

        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, char ->
                table[Pos(row, col)] = char
            }
        }

        input.forEachIndexed { row, line ->
            val indexesOfX = line.findIndexesOfChar('X')
            indexesOfX.forEach { col ->
                val currentPos = Pos(row, col)
                val lines = getLines(table, currentPos)
                doneLine.addAll(lines)
            }
        }
        return doneLine.toSet().size
    }

    val testPattern = listOf("MAS", "SAM")
    fun validateXLine(table: Map<Pos, Char>, currentPos: Pos): Boolean {
        val crossLine1 =
            (-1..1).map { table[Pos(currentPos.first + it, currentPos.second + it)].charOrSpace() }.joinToString("")
        val crossLine2 =
            (-1..1).map { table[Pos(currentPos.first - it, currentPos.second + it)].charOrSpace() }.joinToString("")
        return testPattern.contains(crossLine1) && testPattern.contains(crossLine2)
    }

    fun part2(input: List<String>): Int {
        val table = mutableMapOf<Pos, Char>()
        var validCount = 0
        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, char ->
                table[Pos(row, col)] = char
            }
        }

        input.forEachIndexed { row, line ->
            val indexesOfX = line.findIndexesOfChar('A')
            indexesOfX.forEach { col ->
                val currentPos = Pos(row, col)
                if (validateXLine(table, currentPos)) {
                    validCount++
                }
            }
        }
        return validCount
    }


    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day04_test")
//    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
//    part1(input).println()
    part2(input).println()
}
