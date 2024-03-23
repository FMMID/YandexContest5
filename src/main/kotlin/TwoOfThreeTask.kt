import java.util.*

fun main() {

    val treeMap = mutableSetOf<Num>()

    readLine()
    readLine()!!.split(' ').forEach {
        val num = it.toInt()
        treeMap.add(Num(1, num))
    }

    readLine()
    readLine()!!.split(' ').forEach {
        val num = it.toInt()
        treeMap.add(Num(2, num))
    }

    readLine()
    readLine()!!.split(' ').forEach {
        val num = it.toInt()
        treeMap.add(Num(3, num))
    }

    val result = TreeSet<Int>()
    treeMap.groupBy { it.num }.forEach {
        if (it.value.size >= 2) {
            result.add(it.key)
        }
    }

    println(result.joinToString(" "))
}

data class Num(val stingNum: Int, val num: Int)
