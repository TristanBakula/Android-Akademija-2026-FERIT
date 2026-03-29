data class Stadium(
    val name: String,
    val capacity: Int,
    val builtYear: Int
) {
    override fun toString(): String {
        return "$name, $capacity capacity, built in $builtYear."
    }
}