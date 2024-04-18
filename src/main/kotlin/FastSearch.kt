fun main() {
    val n = readLine()!!.toInt()
    val listOfNumbers = readLine()!!.split(' ').map(String::toInt).sorted().toIntArray()

    val k = readLine()!!.toInt()
    val fromLToRPairs = List(k) {
        val (l, r) = readLine()!!.split(' ').map(String::toInt)
        Pair(l, r)
    }
    val mapOfAnswers = IntArray(k)

    fromLToRPairs.forEachIndexed { index, pair ->
        mapOfAnswers[index] = countNumbersInRange(listOfNumbers, pair.first, pair.second)
    }

    println(mapOfAnswers.joinToString(" "))
}

fun countNumbersInRange(array: IntArray, left: Int, right: Int): Int {
    val leftIndex = findFirstGreaterOrEqual(array, left)
    val rightIndex = findLastLessOrEqual(array, right)
    return if (leftIndex != -1 && rightIndex != -1 && leftIndex <= rightIndex) {
        rightIndex - leftIndex + 1
    } else {
        0
    }
}

fun findFirstGreaterOrEqual(array: IntArray, target: Int): Int {
    var low = 0
    var high = array.size - 1
    var result = -1
    while (low <= high) {
        val mid = low + (high - low) / 2
        if (array[mid] >= target) {
            result = mid
            high = mid - 1
        } else {
            low = mid + 1
        }
    }
    return result
}

fun findLastLessOrEqual(array: IntArray, target: Int): Int {
    var low = 0
    var high = array.size - 1
    var result = -1
    while (low <= high) {
        val mid = low + (high - low) / 2
        if (array[mid] <= target) {
            result = mid
            low = mid + 1
        } else {
            high = mid - 1
        }
    }
    return result
}
