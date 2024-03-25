package thirdContext

fun main() {
    val firstStringHashMap = hashMapOf<Char, Int>()

    readLine()!!.forEach { char ->
        firstStringHashMap[char] = firstStringHashMap.getOrDefault(char, 0) + 1
    }

    readLine()?.forEach { char ->
        if (char !in firstStringHashMap) {
            println("NO")
            return
        }
        firstStringHashMap[char] = firstStringHashMap[char]!! - 1
    }

    val answer = if (firstStringHashMap.all { it.value <= 0 }) {
        "YES"
    } else {
        "NO"
    }

    println(answer)
}