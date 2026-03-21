

fun main() {

    var username: String = readLine()!!

    val nameToCheck = username.trim().lowercase()

    val isValid = CheckName(nameToCheck)
    println("Je li korisničko ime valjano? $isValid")

}

fun CheckName(name: String): Boolean {

    if(name.isBlank()) return false
    if(name.length < 5 || name.length > 15) return false
    if(!name[0].isLetter()) return false
    if(!CheckForChar(name)) return false
    if(name.contains(" ")) return false

    return true
}

fun CheckForChar(name: String): Boolean {
    for(char in name) {
        if(!char.isLetterOrDigit() && char != '_'){
            return false
        }
    }
    return true
}