package secondContext

import BaseTest
import FishSeller
import io.kotest.datatest.withData
import io.kotest.matchers.equals.shouldBeEqual

class FishSellerTest : BaseTest<FishSeller>() {
    override val testComponent: FishSeller = FishSeller()

    init {
        context("test fish seller tests") {
            withData(
                InputData(soiledFishDays = 2, listOfPrices = listOf(1, 2, 3, 4, 5), result = 2),
                InputData(soiledFishDays = 2, listOfPrices = listOf(5, 4, 3, 2, 1), result = 0),
                InputData(soiledFishDays = 3, listOfPrices = listOf(3, 4, 1, 8, 9, 3), result = 8),
                InputData(soiledFishDays = 1, listOfPrices = listOf(1, 2), result = 1),
                InputData(soiledFishDays = 10, listOfPrices = listOf(6, 7, 5, 5, 10, 10, 1, 8, 5, 10), result = 9),
            ) {
                it.test()
            }
        }
    }

    private fun InputData.test() {
        testComponent.calculateMaxProfit(
            soiledFishDays,
            listOfPrices,
        ) shouldBeEqual result
    }

    private data class InputData(
        val soiledFishDays: Long,
        val listOfPrices: List<Long>,
        val result: Long
    )
}