package backend.dev.config

class Config(
    val host: String,
    val port: Int,
    val databaseHost: String ,
    val databasePort: String,
) {

    companion object {
        val DATABASENAME = System.getenv()["DATABASENAME"]
        val DATABASEUSER = System.getenv()["DATABASEUSER"]
        val DATABASEPASSWORD = System.getenv()["DATABASEPASSWORD"]
    }
}