package firstContext

import firstContext.FootballCommentator.FirstMatchPlace
import firstContext.FootballCommentator.Match
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.equals.shouldBeEqual

class FootballCommentatorTest : FunSpec() {

    private val footballCommentator = FootballCommentator()

    init {
        context("test task examples") {
            withData(
                InputData(
                    firstMatch = Match(firstTeamScore = 0, secondTeamScore = 0),
                    secondMatch = Match(firstTeamScore = 0, secondTeamScore = 0),
                    firstMatchPlace = FirstMatchPlace.HOME,
                    result = 1
                ),
                InputData(
                    firstMatch = Match(firstTeamScore = 0, secondTeamScore = 2),
                    secondMatch = Match(firstTeamScore = 0, secondTeamScore = 3),
                    firstMatchPlace = FirstMatchPlace.HOME,
                    result = 5
                ),
                InputData(
                    firstMatch = Match(firstTeamScore = 0, secondTeamScore = 2),
                    secondMatch = Match(firstTeamScore = 0, secondTeamScore = 3),
                    firstMatchPlace = FirstMatchPlace.GUESTS,
                    result = 6
                ),
                InputData(
                    firstMatch = Match(firstTeamScore = 2, secondTeamScore = 3),
                    secondMatch = Match(firstTeamScore = 1, secondTeamScore = 0),
                    firstMatchPlace = FirstMatchPlace.GUESTS,
                    result = 0
                ),
                InputData(
                    firstMatch = Match(firstTeamScore = 2, secondTeamScore = 2),
                    secondMatch = Match(firstTeamScore = 1, secondTeamScore = 1),
                    firstMatchPlace = FirstMatchPlace.GUESTS,
                    result = 0
                ),
                InputData(
                    firstMatch = Match(firstTeamScore = 1, secondTeamScore = 1),
                    secondMatch = Match(firstTeamScore = 1, secondTeamScore = 4),
                    firstMatchPlace = FirstMatchPlace.HOME,
                    result = 3
                ),
            ) { inputData -> calculateNeededScore(inputData) }
        }

        context("test custom examples") {
            withData(
                InputData(
                    firstMatch = Match(firstTeamScore = 0, secondTeamScore = 2),
                    secondMatch = Match(firstTeamScore = 0, secondTeamScore = 3),
                    firstMatchPlace = FirstMatchPlace.GUESTS,
                    result = 6
                ),
                InputData(
                    firstMatch = Match(firstTeamScore = 0, secondTeamScore = 2),
                    secondMatch = Match(firstTeamScore = 0, secondTeamScore = 3),
                    firstMatchPlace = FirstMatchPlace.GUESTS,
                    result = 6
                )
            ) { inputData -> calculateNeededScore(inputData) }
        }
    }

    private fun calculateNeededScore(inputData: InputData) {
        with(inputData) {
            footballCommentator.calculateNeededScore(
                firstMatch,
                secondMatch,
                firstMatchPlace
            ) shouldBeEqual result
        }
    }

    data class InputData(
        val firstMatch: Match,
        val secondMatch: Match,
        val firstMatchPlace: FirstMatchPlace,
        val result: Int
    )
}