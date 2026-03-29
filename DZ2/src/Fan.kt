data class Fan(
    override val name: String,
    override var age: Int,
    override val nationality: String,
    val membershipId: Int,
    val favoritePlayer: String
) : Person(name, age, nationality) {

}