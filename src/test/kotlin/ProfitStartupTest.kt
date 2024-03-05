import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.equals.shouldBeEqual

class ProfitStartupTest : FunSpec() {

    private val profitStartup = ProfitStartup()

    init {
        context("test profit startup with samples of context") {
            withData(
                InputData(startProfit = 21, numOfFounders = 108, daysOfProfit = 1, result = "216"),
                InputData(startProfit = 5, numOfFounders = 12, daysOfProfit = 4, result = "-1")
            ) { inputData ->
                profitStartup.calculateProfit(
                    inputData.startProfit,
                    inputData.numOfFounders,
                    inputData.daysOfProfit
                ) shouldBeEqual inputData.result
            }
        }

        context("test profit startup") {
            withData(
                InputData(startProfit = 15, numOfFounders = 150, daysOfProfit = 2, result = "1500"),
                InputData(startProfit = 18, numOfFounders = 23, daysOfProfit = 3, result = "18400"),
            ) { inputData ->
                profitStartup.calculateProfit(
                    inputData.startProfit,
                    inputData.numOfFounders,
                    inputData.daysOfProfit
                ) shouldBeEqual inputData.result
            }
        }
    }

    private data class InputData(
        val startProfit: Long,
        val numOfFounders: Long,
        val daysOfProfit: Long,
        val result: String
    )
}