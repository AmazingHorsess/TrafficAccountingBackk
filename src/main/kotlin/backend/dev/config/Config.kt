package backend.dev.config

class Config(
    val host: String,
    val port: Int,
    val databaseHost: String,
    val databasePort: String
) {

    companion object {
        const val DATABASENAME: String = "testdb"
        const val DATABASEUSER: String = "testuser"
        const val DATABASEPASSWORD: String = "testpassword"
    }
}