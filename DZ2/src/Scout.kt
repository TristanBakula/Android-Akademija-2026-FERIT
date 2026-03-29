class Scout(
    name: String,
    age: Int,
    nationality: String,
    salary: Double,
    val region: String
) : Worker(name, age, nationality, salary) {

    override fun doWork(): String {
        return "$name is scouting in $region"
    }
}