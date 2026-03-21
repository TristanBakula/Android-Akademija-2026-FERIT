

fun main() {

    var stepsPerDay: List<Int> =
        listOf(4500, 12000, 8000, 15000, 3000, 11000, 9500)

    var weeklySteps: Int = 0

    for(dailySteps in stepsPerDay) {
        weeklySteps += dailySteps
    }

    println("Your weekly steps is: $weeklySteps")

    var i: Int = 0
    while(i < stepsPerDay.size) {
        if(stepsPerDay[i] > 10000) {
            println("You surpassed 10k steps on day ${i+1}")
            break
        }
        i++
    }


}