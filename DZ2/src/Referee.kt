class Referee(
    name: String,
    age: Int,
    nationality: String,
    salary: Double
) : Worker(name, age, nationality, salary) {

    override fun doWork(): String {
        return "Refereeing a match..."
    }
}