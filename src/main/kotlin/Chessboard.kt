class Chessboard() {

    fun calculateFigurePerimeter(listOfPoints: List<Coordinate>): Long {
        var perimeter = 0L
        val closelyPoints = listOf(
            Coordinate(x = 0, y = 1),
            Coordinate(x = 0, y = -1),
            Coordinate(x = 1, y = 0),
            Coordinate(x = -1, y = 0)
        )

        listOfPoints.forEach { coordinate ->

            var neighbors = 0

            closelyPoints.forEach { moveDirection ->
                val dx = coordinate.x + moveDirection.x
                val dy = coordinate.y + moveDirection.y

                if (listOfPoints.contains(Coordinate(dx, dy))) {
                    neighbors++
                }
            }

            perimeter += 4 - neighbors
        }

        return perimeter
    }

    data class Coordinate(val x: Int, val y: Int)
}