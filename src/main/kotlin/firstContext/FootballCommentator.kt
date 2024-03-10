package firstContext

class FootballCommentator {
    fun calculateNeededScore(firstMatch: Match, secondMatch: Match, firstMatchPlace: FirstMatchPlace): Int {

        val currentResultMatch = firstMatch + secondMatch

        if (!currentResultMatch.isFirstTeamLose()) return 0

        val isFirstTeamWinGuestMatch = if (firstMatchPlace == FirstMatchPlace.GUESTS) {
            firstMatch.firstTeamScore > secondMatch.secondTeamScore
        } else {
            secondMatch.firstTeamScore > firstMatch.secondTeamScore
        }

        if (currentResultMatch.isDrawMatch() && isFirstTeamWinGuestMatch) return 0

        if (currentResultMatch.isDrawMatch() && !isFirstTeamWinGuestMatch) return 1

        val scoreDifference = currentResultMatch.secondTeamScore - currentResultMatch.firstTeamScore

        return when {
            isFirstTeamWinGuestMatch -> scoreDifference
            firstMatchPlace == FirstMatchPlace.HOME && firstMatch.firstTeamScore == 0 -> scoreDifference
            firstMatchPlace == FirstMatchPlace.HOME && secondMatch.firstTeamScore + scoreDifference > secondMatch.secondTeamScore -> scoreDifference
            firstMatchPlace == FirstMatchPlace.HOME && secondMatch.firstTeamScore + scoreDifference > firstMatch.secondTeamScore -> scoreDifference
            else -> scoreDifference + 1
        }
    }

    data class Match(
        val firstTeamScore: Int,
        val secondTeamScore: Int
    ) {

        fun isFirstTeamLose(): Boolean {
            return firstTeamScore <= secondTeamScore
        }

        fun isDrawMatch(): Boolean {
            return firstTeamScore == secondTeamScore
        }

        operator fun plus(other: Match): Match {
            return Match(
                firstTeamScore = this.firstTeamScore + other.firstTeamScore,
                secondTeamScore = this.secondTeamScore + other.secondTeamScore
            )
        }
    }

    enum class FirstMatchPlace(val value: String) {
        HOME("1"),
        GUESTS("2");

        companion object {
            operator fun invoke(place: String): FirstMatchPlace {
                return when (place) {
                    HOME.value -> HOME
                    GUESTS.value -> GUESTS
                    else -> throw IllegalArgumentException("value - $place don't present in FirstMatchPlace")
                }
            }
        }
    }
}
