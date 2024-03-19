package secondContext

fun calculateSections(listOfNumber: List<Long>): Pair<Int, String> {
    val listOfSections = arrayListOf<Long>()
    var sizeOfSections = 0

    var currentSectionSize = 0L
    var currentSectionMinElement = 0L

    listOfNumber.forEach { number ->
        if (currentSectionSize == 0L) {
            sizeOfSections++
            currentSectionMinElement = number
            currentSectionSize = 1
        } else {
            if (number > currentSectionSize && currentSectionMinElement >= currentSectionSize + 1) {
                currentSectionMinElement = minOf(number, currentSectionMinElement)
                currentSectionSize++
            } else {
                listOfSections.add(currentSectionSize)
                sizeOfSections++
                currentSectionMinElement = number
                currentSectionSize = 1
            }
        }
    }

    if (listOfSections.size < sizeOfSections) {
        listOfSections.add(currentSectionSize)
    }

    return Pair(sizeOfSections, listOfSections.joinToString(" "))
}