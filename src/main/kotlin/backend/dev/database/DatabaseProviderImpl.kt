package backend.dev.database

import backend.dev.config.Config
import backend.dev.database.dao.tables.TrafficLogs
import backend.dev.database.dao.tables.TrafficUsers
import kotlinx.coroutines.newFixedThreadPoolContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.CoroutineContext
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseProviderImpl: DatabaseProvider, KoinComponent {

    private val config by inject<Config>()
    private val dispatcher: CoroutineContext

    init {
        dispatcher = newFixedThreadPoolContext(5,"database-pool")

    }

    override fun init() {
        Database.connect(hikari(config))
        transaction {
            create(TrafficUsers)
            create(TrafficLogs)
        }
    }

    private fun hikari(mainConfig: Config): HikariDataSource {
        return HikariConfig().apply {
            driverClassName = "com.mysql.cj.jdbc.Driver"
            jdbcUrl = "jdbc:mysql://${mainConfig.databaseHost}:${mainConfig.databasePort}/${Config.DATABASENAME}"
            username = Config.DATABASEUSER
            password = Config.DATABASEPASSWORD
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        }.let { HikariDataSource(it) }
    }

    override suspend fun <T> dbQuery(block: () -> T): T = withContext(dispatcher){
        transaction { block() }
    }
}