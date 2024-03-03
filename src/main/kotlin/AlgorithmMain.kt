fun main() {
    val (firstTeamScoreMatch1, secondTeamScoreMatch1) = readLine()!!.split(':').map(String::toInt)
    val (firstTeamScoreMatch2, secondTeamScoreMatch2) = readLine()!!.split(':').map(String::toInt)
    val firstMatchPlaceValue = readLine()!!

    val needWinFirstTeamScore = FootballCommentator().calculateNeededScore(
        firstMatch = FootballCommentator.Match(firstTeamScoreMatch1, secondTeamScoreMatch1),
        secondMatch = FootballCommentator.Match(firstTeamScoreMatch2, secondTeamScoreMatch2),
        firstMatchPlace = FootballCommentator.FirstMatchPlace(firstMatchPlaceValue)
    )

    print(needWinFirstTeamScore)
}
