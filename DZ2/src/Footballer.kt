class Footballer(
    name: String,
    age: Int,
    nationality: String,
    salary: Double,
    val position: Position,
    val shirtNumber: Int,
    var stats: FootballerStatistics
) : Worker(name, age, nationality, salary) {


    override fun doWork(): String {
        return "Training hard..."
    }

    fun playMatch() {
        stats = stats.addMatch()
    }

    fun scoreGoal() {
        stats = stats.addGoal()
    }

    fun getAssist() {
        stats = stats.addAssists()
    }

    fun getYellowCard() {
        stats = stats.addYellowCards()
    }

    fun getRedCard() {
        stats = stats.addRedCards()
    }
}