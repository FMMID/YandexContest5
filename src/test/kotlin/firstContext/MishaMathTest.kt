package firstContext

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.equals.shouldBeEqual

class MishaMathTest : FunSpec() {

    private val mishaMath = MishaMath()

    init {
        context("test misha math with context samples") {
            withData(
                InputData(arrayOfNumbers = arrayOf(5, 7, 2), result = "x+"),
                InputData(arrayOfNumbers = arrayOf(4, -5), result = "+"),
                InputData(arrayOfNumbers = arrayOf(-432300451, 509430974, 600857889, 140418957), result = "+xx"),
            ) { inputData ->
                mishaMath.getOperationSequence(
                    inputData.arrayOfNumbers
                ) shouldBeEqual inputData.result
            }
        }
    }

    private data class InputData(val arrayOfNumbers: Array<Long>, val result: String)
}