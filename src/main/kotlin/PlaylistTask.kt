import java.util.TreeMap

fun main() {
    val numOfUsers = readLine()!!.toInt()

    val hashMap = TreeMap<String, Int>()

    repeat(numOfUsers) {
        val numOfTracks = readLine()!!.toLong()
        readLine()!!.split(' ').forEach { song ->
            hashMap[song] = hashMap.getOrDefault(song, 0) + 1
        }
    }

    val result = hashMap.filterValues { it == numOfUsers }.keys

    println("${result.size}\n${result.joinToString(" ")}")
}
