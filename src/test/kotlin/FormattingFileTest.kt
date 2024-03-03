import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.equals.shouldBeEqual

class FormattingFileTest : FunSpec() {

    private val formattingFile = FormattingFile()

    init {
        context("test formatting File") {
            withData(
                InputData(listOfLinesSpaces = listOf(1, 4, 12, 9, 0), result = 8),
                InputData(listOfLinesSpaces = listOf(5, 11, 2), result = 8),
                InputData(listOfLinesSpaces = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), result = 27),
            ) { inputData ->
                formattingFile.calcMinOperation(inputData.listOfLinesSpaces) shouldBeEqual inputData.result
            }
        }

    }

    private data class InputData(val listOfLinesSpaces: List<Int>, val result: Int)
}