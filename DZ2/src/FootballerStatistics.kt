data class FootballerStatistics(
    val matches: Int,
    val goals: Int,
    val assists: Int,
    val yellowCards: Int,
    val redCards: Int
) {
    fun addMatch() = copy(matches = matches + 1)

    fun addGoal() = copy(goals = goals + 1)

    fun addAssists() = copy(assists = assists + 1)

    fun addYellowCards() = copy(yellowCards = yellowCards + 1)

    fun addRedCards() = copy(redCards = redCards + 1)
}
