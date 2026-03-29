import java.time.LocalDate
import java.time.Period

class President(
    name: String,
    age: Int,
    salary: Double,
    nationality: String,
    private val startDate: LocalDate,
    private val termYears: Int
) : Worker(name, age, nationality, salary) {

    override fun doWork(): String {
        return "Managing the club..."
    }

    override fun toString(): String {
        return "$name, $age, $nationality, $salary/year, Elected: $startDate"
    }

    fun printTimeLeft() {
        val end = startDate.plusYears(termYears.toLong())
        val today = LocalDate.now()

        if(today.isAfter(end)) {
            println("Mandate is over!")
        }

        val period = Period.between(today, end)
        println("${period.years} years, ${period.months} months, ${period.days} days.")
    }
}