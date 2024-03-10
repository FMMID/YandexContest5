package secondContext

class MinRectangle {

    fun getRectangle(listOfCoordinates: List<Coordinate>): ResultRectangle {

        val minXY = Coordinate(Long.MAX_VALUE, Long.MAX_VALUE)
        val maxXY = Coordinate(Long.MIN_VALUE, Long.MIN_VALUE)

        listOfCoordinates.forEach { coordinate ->
            minXY.apply {
                x = minOf(coordinate.x, x)
                y = minOf(coordinate.y, y)
            }

            maxXY.apply {
                x = maxOf(coordinate.x, x)
                y = maxOf(coordinate.y, y)
            }
        }

        return ResultRectangle(minXY, maxXY)
    }

    data class ResultRectangle(private val bottomLeft: Coordinate, private val topRightCoordinate: Coordinate) {

        fun printResult() {
            println("${bottomLeft.x} ${bottomLeft.y} ${topRightCoordinate.x} ${topRightCoordinate.y}")
        }
    }

    data class Coordinate(var x: Long, var y: Long)
}