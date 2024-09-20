package backend.dev.database.dao.tables

import backend.dev.database.dao.TrafficUsersDao
import backend.dev.database.dao.tables.TrafficUsers.src_ip
import backend.dev.database.dao.tables.TrafficUsers.username
import backend.dev.model.PutUsernameInIp
import backend.dev.model.TrafficUser as ModelTrafficUser
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object TrafficUsers : Table("users"), TrafficUsersDao {
    val src_ip = varchar("src_ip",15).uniqueIndex()
    val username = varchar("username", 30).nullable()

    override fun getAllTrafficUsers(): List<ModelTrafficUser> {
        return transaction {
            TrafficUsers.selectAll().map { it.mapRowToTrafficUsers() }
        }
    }

    override fun updateTrafficUsername(putUsernameBody: PutUsernameInIp) {
        return transaction {
            TrafficUsers.update({ TrafficUsers.src_ip eq src_ip}) {
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