abstract class Worker(
    name: String,
    age: Int,
    nationality: String,
    protected val salary: Double,
): Person(name, age, nationality) {

    override fun toString(): String {
        return "$name, $age, $nationality, $salary/year."
    }
    abstract fun doWork(): String
}