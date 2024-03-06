class MishaMath {
    fun getOperationSequence(arrayOfNumbers: Array<Long>): String {
        val resultBuilder = StringBuilder()
        var isCurrentValueEven = arrayOfNumbers[0] % 2 == 0L

        for (index in 1 until arrayOfNumbers.size) {
            val isSecondValueOdd = arrayOfNumbers[index] % 2 != 0L

            resultBuilder.append(getNeedOperation(isBothValueOdd = !isCurrentValueEven && isSecondValueOdd))

            isCurrentValueEven = isCurrentValueEven && !isSecondValueOdd
        }

        return resultBuilder.toString()
    }

    private fun getNeedOperation(isBothValueOdd: Boolean): String {
        return if (isBothValueOdd) "x" else "+"
    }
}