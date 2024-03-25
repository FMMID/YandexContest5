package thirdContext

import kotlin.math.abs

fun main() {
    val (numCount, range) = readLine()!!.split(' ').map(String::toInt)
    val numbers = readLine()!!.split("\\s+".toRegex())

    val indexesHasMap = hashMapOf<String, ArrayList<Int>>()

    numbers.forEachIndexed { index, num ->
        if (indexesHasMap.containsKey(num)) {
            indexesHasMap[num]?.add(index)
        } else {
            indexesHasMap[num] = arrayListOf(index)
        }
    }

    indexesHasMap.entries.forEach { (_, indexes) ->
        if (indexes.size > 1) {
            for (index in 0 until indexes.size - 1) {
                if (abs(indexes[index] - indexes[index + 1]) <= range) {
                    println("YES")
                    return
                }
            }
        }
    }

    println("NO")
}
