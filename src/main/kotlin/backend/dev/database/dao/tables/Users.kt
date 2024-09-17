package backend.dev.database.dao.tables

import backend.dev.database.dao.TrafficUsersDao
import backend.dev.database.dao.tables.Users.src_ip
import backend.dev.database.dao.tables.Users.username
import backend.dev.model.PutUsernameInIp
import backend.dev.model.TrafficUser as ModelTrafficUser
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Users : Table("users"), TrafficUsersDao {
    val src_ip = varchar("src_ip",15).uniqueIndex()
    val username = varchar("username", 30).nullable()

    override fun getAllUsers(): List<ModelTrafficUser> {
        return transaction {
            Users.selectAll().map { it.mapRowToTrafficUsers() }
        }
    }

    override fun insertUser(putUsernameBody: PutUsernameInIp) {
        return transaction {
            Users.insert {
                it[src_ip] = putUsernameBody.source_ip
                it[username] = putUsernameBody.username
            }
        }
    }

    override fun updateUsername(putUsernameBody: PutUsernameInIp) {
        return transaction {
            Users.update({ Users.src_ip eq src_ip}) {
                it[username] = putUsernameBody.username
            }
        }
    }
}

fun ResultRow.mapRowToTrafficUsers() =
    ModelTrafficUser(
        src_ip = this[src_ip],
        username = this[username],
    )