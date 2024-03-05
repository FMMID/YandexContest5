import java.math.BigInteger

class ProfitStartup {
    private val terminateNumber = BigInteger.valueOf(-1L)
    fun calculateProfit(startProfit: Long, numOfFounders: Long, daysOfProfit: Long): String {
        val numOfFoundersBI = BigInteger.valueOf(numOfFounders)
        var currentProfit = BigInteger.valueOf(startProfit)
        var lastDay = 0L

        for (day in 1..daysOfProfit) {
            lastDay = day

            currentProfit = getNeededNumber(currentProfit, numOfFoundersBI)

            if (currentProfit == terminateNumber) return terminateNumber.toString()

            if (hasTwoZerosAtEnd(currentProfit.toString())) break
        }

        val remainingDays = (daysOfProfit - lastDay).toInt()
        val tailOfNumber = "0".repeat(remainingDays)

        return currentProfit.toString() + tailOfNumber
    }

    private fun hasTwoZerosAtEnd(input: String): Boolean {
        return Regex(pattern = ".*00$").matches(input)
    }

    private fun getNeededNumber(profit: BigInteger, numOfFoundersBigInteger: BigInteger): BigInteger {
        for (index in 0..9) {
            val newNumber = profit.multiply(BigInteger.TEN).add(BigInteger.valueOf(index.toLong()))
            if (newNumber.mod(numOfFoundersBigInteger) == BigInteger.ZERO) {
                return newNumber
            }
        }
        return terminateNumber
    }
}
