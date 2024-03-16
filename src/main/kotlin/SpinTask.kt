import kotlin.math.ceil

class SpinTask() {

    fun calculateMax(listOfNumbers: Array<Int>, minV: Long, maxV: Long, dk: Long): Int {

        val arraySize = listOfNumbers.size
        var localMaxNumber = 0
        val checkedIndex = mutableSetOf<Int>()

        val steps = if (maxV - minV == dk) 2 else ceil((maxV - minV) / dk.toFloat()).toInt()

        val firstStep = minV / dk
        val firstStepRemain = minV % dk

        var startIndex = if (firstStepRemain == 0L) {
            firstStep - 1
        } else {
            firstStep
        } % arraySize

        if (steps == 0) {
            val rightSpin = if (startIndex <= 0) 0 else (startIndex % arraySize).toInt()
            val leftSpin = (if (rightSpin == 0) 0L else arraySize - rightSpin).toInt()
            return maxOf(localMaxNumber, listOfNumbers[rightSpin], listOfNumbers[leftSpin])
        }

        repeat(steps) {
            val rightSpin = if (startIndex <= 0) 0 else (startIndex % arraySize).toInt()
            val leftSpin = (if (rightSpin == 0) 0L else arraySize - rightSpin).toInt()
            localMaxNumber = maxOf(localMaxNumber, listOfNumbers[rightSpin], listOfNumbers[leftSpin])
            checkedIndex.add(rightSpin)

            if (startIndex + 1 >= arraySize) {
                startIndex = 0
            } else {
                startIndex++
            }

            if (checkedIndex.size == arraySize) return localMaxNumber
        }

        return localMaxNumber
    }
}