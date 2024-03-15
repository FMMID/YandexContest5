class AmbitiousSnail {

    private val berryComparator = Comparator<Berry> { localMaxBerry, berry ->
        if (localMaxBerry.getWeight() + berry.lift > berry.getWeight() + localMaxBerry.lift) {
            if (localMaxBerry.getWeight() < 0) {
                if (berry.getWeight() > 0) {
                    1
                } else if (berry.getWeight() < 0 && berry.lift > localMaxBerry.lift) {
                    1
                } else {
                    -1
                }
            } else {
                -1
            }
        } else {
            if (berry.getWeight() < 0) {
                if (localMaxBerry.getWeight() > 0) {
                    -1
                } else if (localMaxBerry.getWeight() < 0 && localMaxBerry.lift > berry.lift) {
                    -1
                } else {
                    1
                }
            } else {
                1
            }
        }
    }

    fun calculateHeight(listOfBerries: List<Berry>): ResultHeight {
        var currentHeight = 0L
        var maxHeight = 0L
        val feedingOrder = mutableListOf<Int>()
        val sortedBerries = listOfBerries.sortedWith(berryComparator)

        sortedBerries.forEach { berry ->
            currentHeight += berry.lift
            maxHeight = maxOf(currentHeight, maxHeight)
            feedingOrder.add(berry.order)
            currentHeight -= berry.decline
        }

        return ResultHeight(maxHeight, feedingOrder)
    }

    data class ResultHeight(
        val maxHeight: Long,
        val sortedBerries: List<Int>
    ) {
        fun printResult() {
            println(maxHeight)
            println(sortedBerries.joinToString(" "))
        }
    }

    data class Berry(val lift: Long, val decline: Long, val order: Int) {
        fun getWeight(): Long {
            return lift - decline
        }
    }
}