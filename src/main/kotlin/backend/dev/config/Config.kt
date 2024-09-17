package backend.dev.config

class Config(
    val host: String,
    val port: Int,
    val databaseHost: String,
    val databasePort: String
) {

    companion object {
        val DATABASENAME: String = System.getenv("DATABASENAME")
        val DATABASEUSER: String = System.getenv("DATABASEUSER")
        val DATABASEPASSWORD: String = System.getenv("DATABASEPASSWORD")
    }
}