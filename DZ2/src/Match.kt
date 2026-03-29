class Match(
    val homeTeam: FootballTeam,
    val awayTeam: FootballTeam,
    val homeGoals: Int,
    val awayGoals: Int,
    val stadium: Stadium,
    val referee: Referee
) {

    override fun toString(): String {
        return "${homeTeam.name} vs ${awayTeam.name}, played at ${stadium.name}, referee: ${referee.name}, result: $homeGoals:$awayGoals"
    }

    fun getResult(): String {
        if(homeGoals == awayGoals)
            return "Draw!"
        else if(homeGoals > awayGoals)
            return "${homeTeam.name} won!"
        else
            return "${awayTeam.name} won!"
    }
}