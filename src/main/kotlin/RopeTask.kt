class RopeTask() {

    fun calculateMinRope(listOfRopes: List<Long>): Long {

        var maxRopeLength = Long.MIN_VALUE
        var sumOfRopes = 0L

        listOfRopes.forEach { rope ->
            maxRopeLength = maxOf(maxRopeLength, rope)
            sumOfRopes += rope
        }

        val remains = sumOfRopes - maxRopeLength

        return if (remains < maxRopeLength) {
            maxRopeLength - remains
        } else {
            maxRopeLength + remains
        }
    }
}