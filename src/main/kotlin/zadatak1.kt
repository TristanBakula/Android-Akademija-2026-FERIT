
fun main() {

    var name: String = "Luka"
    var surname: String = "Modric"
    var email: String? = null
    var age: Int? = 40

    PrintEmailLength(email)

    email = "lukamodric@gmail.com"
    PrintEmailLength(email)

}

fun PrintEmailLength(email: String?) {

    if(email != null) {
        println("Email length: ${email.length}")
    }
    else {
        println("Email is null! Length is 0.")
    }

}