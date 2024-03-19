package secondContext

import io.kotest.datatest.withData
import io.kotest.matchers.equals.shouldBeEqual

class SpinTest : BaseTest<SpinTask>() {
    override val testComponent: SpinTask = SpinTask()

    init {
        context("context spin text") {
            withData(
                InputData(listOf(1, 2, 3, 4, 5), 3, 5, 2, 5),
                InputData(listOf(1, 2, 3, 4, 5), 15, 15, 2, 4),
                InputData(listOf(5, 4, 3, 2, 1), 2, 5, 2, 5),
                InputData(listOf(744, 43, 468, 382), 20, 48, 12, 468),
                InputData(listOf(1, 2, 3, 4, 5), 1, 2, 1, 5)
            ) { it.test() }
        }
    }

    private fun InputData.test() {
        testComponent.calculateMax(
            listOfNum.toTypedArray(),
            minV,
            maxV,
            dk
        ) shouldBeEqual result
    }

    private data class InputData(
        val listOfNum: List<Int>,
        val minV: Long,
        val maxV: Long,
        val dk: Long,
        val result: Int
    )
}