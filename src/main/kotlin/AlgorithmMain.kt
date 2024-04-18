import java.math.BigInteger

fun main() {
    val numOfCells = readLine()!!.toLong()

    var leftBound = 0L
    var rightBound = numOfCells

    if (numOfCells == 0L || numOfCells == 1L) {
        println(numOfCells)
        return
    }

    var result = 0L

    while (leftBound < rightBound) {
        val midValue = (leftBound + rightBound) / 2
        if (checkCondition(midValue, numOfCells)) {
            rightBound = midValue
        } else {
            result = midValue
            leftBound = midValue + 1
        }
    }

    println(result)
}

fun checkCondition(currentNum: Long, numOfCells: Long): Boolean {
    return numOfCells.toBigInteger() - calculateSum(currentNum.toBigInteger()) < BigInteger.ZERO
}

tailrec fun calculateSum(
    num: BigInteger,
    startNum: BigInteger = BigInteger.ONE,
    sum: BigInteger = BigInteger.ZERO,
    stopNum: BigInteger = num
): BigInteger {
    if (stopNum <= BigInteger.ZERO) return BigInteger.ZERO

    val localSum = if (startNum == BigInteger.ONE) {
        sum + startNum * num
    } else {
        sum + startNum * (num + BigInteger.ONE)
    }

    return if (startNum == stopNum) {
        localSum
    } else {
        calculateSum(num.dec(), startNum.inc(), localSum, stopNum)
    }
}
