class Manager(
    name: String,
    age: Int,
    nationality: String,
    salary: Double,
    val winRate: Double,
) : Worker(name, age, nationality, salary) {

    override fun doWork(): String {
        return "Making up new tactics..."
    }

    override fun toString(): String {
        return "$name, $age ($nationality), $winRate% win rate."
    }
}