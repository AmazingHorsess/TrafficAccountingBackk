package backend.dao

import backend.dev.config.Config
import backend.dev.database.dao.tables.TrafficLogs
import backend.dev.database.dao.tables.TrafficUsers
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.minus
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach 

abstract class BaseDaoTest {

    @BeforeEach
    open fun setup() {
        val dbuser = "root"
        val dbpassword = ""
        Database.connect(
            url = "jdbc:mysql://localhost:3306/test",
            driver = "com.mysql.cj.jdbc.Driver",
            user = dbuser,
            password = dbpassword
        )
    }


    fun withTables(vararg tables: Table, test: Transaction.() -> Unit) {
        transaction {

            SchemaUtils.create(*tables)
            try {
                test()
                commit()

            } finally {
                SchemaUtils.drop(*tables)
                commit()
            }

        }
    }
}