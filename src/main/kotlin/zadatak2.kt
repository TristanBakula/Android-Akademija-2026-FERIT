import kotlin.math.round


fun main() {

    var productCode: Int = readLine()!!.toInt()
    var productPrice: Double = readLine()!!.toDouble()
    var paidMoney: Double = readLine()!!.toDouble()

    var soda = when(productCode) {
        1 -> "Voda"
        2 -> "Cola"
        3 -> "Sok"
        4 -> "Kava"
        else -> ""
    }

    if(paidMoney >= productPrice) {
        val change = paidMoney - productPrice
        println("Filling your cup with $soda...")
        println("Your change is %.2f.".format(change))
    }
    else{
        val missing = productPrice - paidMoney
        println("You need %.2f more for soda.".format(missing))
    }
}