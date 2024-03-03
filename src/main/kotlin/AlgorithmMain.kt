fun main() {
    val numOfLines = readLine()!!.toInt()
    val listOfLinesNeededSpaces = mutableListOf<Int>()

    for (index in 0 until numOfLines) {
        listOfLinesNeededSpaces.add(readLine()!!.toInt())
    }
}
