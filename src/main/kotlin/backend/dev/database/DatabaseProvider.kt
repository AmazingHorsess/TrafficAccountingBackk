package backend.dev.database

interface DatabaseProvider {
    fun init()
    suspend fun <T> dbQuery(block: () -> T): T
}

