fun main() {
    val matrix: MutableList<CharArray> = mutableListOf()

    repeat(8) {
        matrix.add(readLine()!!.trim().toCharArray())
    }
}
