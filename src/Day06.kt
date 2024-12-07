fun main() {

    data class Point(var x: Int, var y: Int)

    val GUARD_SYMBOLS = listOf('>','<','^','v')

    fun part1(input: List<String>): Int {
        val map = mutableMapOf<Point, Char>()
        val distinctSteps = mutableSetOf<Point>()

        var guard = Point(0, 0)
        var direction = '^'

        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                if(GUARD_SYMBOLS.contains(char)) {
                    guard = Point(x, y)
                    map[guard] = '.'
                } else {
                    map[Point(x, y)] = char
                }
            }
        }

        var exited = false

        while(exited == false) {
            distinctSteps.add(guard)
            when(direction) {
                '^' -> {
                    val nextStep = map[Point(guard.x, guard.y -1)]
                    if(nextStep == null) {
                        exited = true
                    } else {
                        if(nextStep == '.') {
                            guard = Point(guard.x, guard.y - 1)
                        } else if(nextStep == '#') {
                            direction = '>'
                            guard = Point(guard.x + 1, guard.y)
                        }
                    }
                }
                '>' -> {
                    val nextStep = map[Point(guard.x + 1, guard.y)]
                    if(nextStep == null) {
                        exited = true
                    } else {
                        if(nextStep == '.') {
                            guard = Point(guard.x + 1, guard.y)
                        } else if(nextStep == '#') {
                            direction = 'v'
                            guard = Point(guard.x, guard.y + 1)
                        }
                    }
                }
                'v' -> {
                    val nextStep = map[Point(guard.x, guard.y + 1)]
                    if(nextStep == null) {
                        exited = true
                    } else {
                        if(nextStep == '.') {
                            guard = Point(guard.x, guard.y + 1)
                        } else if(nextStep == '#') {
                            direction = '<'
                            guard = Point(guard.x - 1, guard.y)
                        }
                    }
                }
                '<' -> {
                    val nextStep = map[Point(guard.x - 1, guard.y)]
                    if(nextStep == null) {
                        exited = true
                    } else {
                        if(nextStep == '.') {
                            guard = Point(guard.x - 1, guard.y)
                        } else if(nextStep == '#') {
                            direction = '^'
                            guard = Point(guard.x, guard.y - 1)
                        }
                    }
                }
            }
        }
        println(distinctSteps)
        return distinctSteps.size
    }

    fun Point.hasBlockerOnRight(map: Map<Point, Char>, currentGuardDirection: Char): Boolean {
        var hasBlocker = false
        var reachedEnd = false
        var currentPoint = this.copy()
        do {
            currentPoint = when(currentGuardDirection) {
                '^' -> Point(currentPoint.x +1, currentPoint.y)
                '>' -> Point(currentPoint.x, currentPoint.y + 1)
                'v' -> Point(currentPoint.x - 1, currentPoint.y)
                '<' -> Point(currentPoint.x, currentPoint.y - 1)
                else -> throw IllegalArgumentException("Invalid direction")
            }
            if(map[currentPoint] == '#') {
                hasBlocker = true
            } else if (map[currentPoint] == null) {
                reachedEnd = true
            }
        } while(!hasBlocker && !reachedEnd)

        return hasBlocker
    }

    fun Point.goesInCircle(map: Map<Point, Char>, currentDirection: Char): Boolean {
        val visitedMap = mutableMapOf<Pair<Point, Char>, Boolean>()
        var guardPosition = this.copy()
        var direction = currentDirection
        var exited = false
        var goesInCircle = false
        do {
            if (visitedMap.contains(Pair(guardPosition, direction))) {
                goesInCircle = true
            } else {
                visitedMap[Pair(guardPosition, direction)] = true
            }

            val nextPosition = when (direction) {
                '^' -> Point(guardPosition.x, guardPosition.y - 1)
                '>' -> Point(guardPosition.x + 1, guardPosition.y)
                'v' -> Point(guardPosition.x, guardPosition.y + 1)
                '<' -> Point(guardPosition.x - 1, guardPosition.y)
                else -> throw IllegalArgumentException("Invalid direction")
            }

            val nextStep = map[nextPosition]

            if (nextStep == null) {
                exited = true
            } else if (nextStep == '#') {
                direction = when (direction) {
                    '^' -> '>'
                    '>' -> 'v'
                    'v' -> '<'
                    '<' -> '^'
                    else -> throw IllegalArgumentException("Invalid direction")
                }
            } else if (nextStep == '.') {
                guardPosition = nextPosition
            }
        } while (!exited && !goesInCircle)

        return goesInCircle
    }

    fun part2(input: List<String>): Int {
        val map = mutableMapOf<Point, Char>()
        var guard = Point(0, 0)
        var direction = '^'

        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                if(GUARD_SYMBOLS.contains(char)) {
                    guard = Point(x, y)
                    map[guard] = '.'
                } else {
                    map[Point(x, y)] = char
                }
            }
        }

        val originPoint = guard.copy()
        val originalDirection = direction

        var exited = false
        val additionalPoints = mutableSetOf<Point>()

        while(exited == false) {
            val nextPoint = when(direction) {
                '^' -> Point(guard.x, guard.y - 1)
                '>' -> Point(guard.x + 1, guard.y)
                'v' -> Point(guard.x, guard.y + 1)
                '<' -> Point(guard.x - 1, guard.y)
                else -> throw IllegalArgumentException("Invalid direction")
            }

            if(map[nextPoint] == '.') {
                if(nextPoint != originPoint && guard.hasBlockerOnRight(map, direction)) {
                    val mapWithAddition = map.toMutableMap()
                    mapWithAddition[nextPoint] = '#'
                    if(originPoint.goesInCircle(mapWithAddition, originalDirection)) {
                        additionalPoints.add(nextPoint)
                    }
                }
                guard = nextPoint
            } else if (map[nextPoint] == '#') {
                direction = when(direction) {
                    '^' -> '>'
                    '>' -> 'v'
                    'v' -> '<'
                    '<' -> '^'
                    else -> throw IllegalArgumentException("Invalid direction")
                }
            } else if (map[nextPoint] == null) {
                exited = true
            }
        }
        return additionalPoints.size
    }


    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day06_test")
//    check(part1(testInput) == 41)
    check(part2(testInput) == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day06")
//    part1(input).println()
    part2(input).println()
}
