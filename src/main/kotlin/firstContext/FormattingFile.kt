package firstContext

private const val NEED_BACKSPACE_OPERATION = 3

class FormattingFile {
    fun calcMinOperation(listOfLinesNeededSpaces: List<Int>): Long {
        var minCountOfOperation: Long = 0

        for (lineNeededSpaces in listOfLinesNeededSpaces) {
            if (lineNeededSpaces == 0) continue

            var modifiedLineNeededSpaces = lineNeededSpaces

            val tabOperationCount = modifiedLineNeededSpaces / 4

            if (tabOperationCount > 0) {
                modifiedLineNeededSpaces %= 4
                minCountOfOperation += tabOperationCount
            }

            if (modifiedLineNeededSpaces == NEED_BACKSPACE_OPERATION) {
                minCountOfOperation += 2
                continue
            }

            minCountOfOperation += modifiedLineNeededSpaces
        }

        return minCountOfOperation
    }
}