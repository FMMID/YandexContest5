package firstContext

import BaseTest
import firstContext.RunningStadium.Runner
import firstContext.RunningStadium.RunningResult
import io.kotest.datatest.withData
import io.kotest.matchers.equals.shouldBeEqual

class RunningStadiumTest : BaseTest<RunningStadium>() {

    override val testComponent: RunningStadium = RunningStadium()

    init {
        context("test running stadium") {
            withData(
                InputData(
                    stadiumLength = 6,
                    kirill = Runner(startDistance = 3, currentSpeed = 1),
                    anton = Runner(startDistance = 1, currentSpeed = 1),
                    result = RunningResult(isDistanceToStartMatched = true, minMatchTime = 1.0000000000)
                ),
                InputData(
                    stadiumLength = 12,
                    kirill = Runner(startDistance = 8, currentSpeed = 10),
                    anton = Runner(startDistance = 5, currentSpeed = 20),
                    result = RunningResult(isDistanceToStartMatched = true, minMatchTime = 0.3000000000)
                ),
                InputData(
                    stadiumLength = 5,
                    kirill = Runner(startDistance = 0, currentSpeed = 0),
                    anton = Runner(startDistance = 1, currentSpeed = 2),
                    result = RunningResult(isDistanceToStartMatched = true, minMatchTime = 2.0000000000)
                ),
                InputData(
                    stadiumLength = 10,
                    kirill = Runner(startDistance = 7, currentSpeed = -3),
                    anton = Runner(startDistance = 1, currentSpeed = 4),
                    result = RunningResult(isDistanceToStartMatched = true, minMatchTime = 0.8571428571428571)
                )
            ) { it.test() }
        }

        test("first test") {
            InputData(
                stadiumLength = 6,
                kirill = Runner(startDistance = 3, currentSpeed = 1),
                anton = Runner(startDistance = 1, currentSpeed = 1),
                result = RunningResult(isDistanceToStartMatched = true, minMatchTime = 0.3000000000)
            ).test()
        }

        test("second test") {
            InputData(
                stadiumLength = 12,
                kirill = Runner(startDistance = 8, currentSpeed = 10),
                anton = Runner(startDistance = 5, currentSpeed = 20),
                result = RunningResult(isDistanceToStartMatched = true, minMatchTime = 1.0000000000)
            ).test()
        }

        test("third test") {
            InputData(
                stadiumLength = 5,
                kirill = Runner(startDistance = 0, currentSpeed = 0),
                anton = Runner(startDistance = 1, currentSpeed = 2),
                result = RunningResult(isDistanceToStartMatched = true, minMatchTime = 2.0000000000)
            ).test()
        }

        test("fourth test") {
            InputData(
                stadiumLength = 10,
                kirill = Runner(startDistance = 7, currentSpeed = -3),
                anton = Runner(startDistance = 1, currentSpeed = 4),
                result = RunningResult(isDistanceToStartMatched = true, minMatchTime = 0.8571428571)
            ).test()
        }
    }

    private fun InputData.test() {
        testComponent.calculateTimeRunning(
            stadiumLength = stadiumLength,
            kirill = kirill,
            anton = anton,
        ) shouldBeEqual result
    }

    data class InputData(
        val stadiumLength: Long,
        val kirill: Runner,
        val anton: Runner,
        val result: RunningResult
    )
}