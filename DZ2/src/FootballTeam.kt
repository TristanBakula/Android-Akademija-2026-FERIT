class FootballTeam(
    val name: String,
    val nationality: String,
    val yearFounded: Int,
    var president: President,
    var manager: Manager,
    val stadium: Stadium,
    private val players: MutableList<Footballer>,
    val fans: MutableList<Fan>
) {

    override fun toString(): String {
        return "$name : $nationality : Founded - $yearFounded\n" +
                "President : $president\n" +
                "Manager: $manager\n" +
                "Stadium: $stadium\n" +
                "Fans: ${fans.count()}\n" +
                printPlayers()
    }

    fun printPlayers(): String {
        return players.joinToString("\n") { it.toString() }
    }

    fun addPlayer(player: Footballer) = players.add(player)

    fun removePlayer(player: Footballer) = players.remove(player)

    fun changeManager(newManager: Manager){
        manager = newManager
    }

    fun getTopScorer() : Footballer = players.maxBy { it.stats.goals }
    fun getTopAssister() : Footballer = players.maxBy { it.stats.assists }

    fun sortByGoalsScored() : List<Footballer> = players.sortedByDescending { it.stats.goals }
    fun sortByAssists() : List<Footballer> = players.sortedByDescending { it.stats.assists }


    fun addFan(fan: Fan) {
        fans.add(fan)
    }
    fun removeFan(fan: Fan) {
        fans.remove(fan)
    }
}