package backend.dev.database.dao

import backend.dev.model.NetworkTraffic as ModelNetworkTraffic
import backend.dev.model.PutUsernameInIp
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import org.jetbrains.exposed.sql.transactions.transaction

object NetworkTraffic: Table(), TrafficDao {
    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)
    val sourceIp = varchar("source_ip", 15)
    val destinationIp = varchar("destination_ip", 15)
    val packetLength = integer("packet_length")
    val sourcePort = integer("source_port")
    val destinationPort = integer("destination_port")
    val username = varchar("username",25)
    val timestamp = datetime("ts")


    override fun getAllTraffic(): List<ModelNetworkTraffic> {
        return transaction {
            addLogger(StdOutSqlLogger)
            NetworkTraffic.selectAll().map { it.mapRowToTraffic() }
        }
    }

    override fun getTrafficByIp(sourceIp: String): List<ModelNetworkTraffic?> {
        return transaction {
            addLogger(StdOutSqlLogger)
            NetworkTraffic
                .selectAll().where { NetworkTraffic.sourceIp eq sourceIp }
                .map { it.mapRowToTraffic() }
        }
    }

    override fun updateUsernameOnIp(putUsername: PutUsernameInIp): ModelNetworkTraffic? {
        return transaction {
            NetworkTraffic.update({ NetworkTraffic.sourceIp eq putUsername.source_ip }) {
                it[username] = putUsername.username
            }

            NetworkTraffic
                .selectAll().where { sourceIp eq putUsername.source_ip }
                .firstOrNull()?.mapRowToTraffic()
        }
    }


}

fun ResultRow.mapRowToTraffic() =
    ModelNetworkTraffic(
        source_ip = this[NetworkTraffic.sourceIp],
        destination_ip = this[NetworkTraffic.destinationIp],
        source_port = this[NetworkTraffic.sourcePort],
        destination_port = this[NetworkTraffic.destinationPort],
        packet_length = this[NetworkTraffic.packetLength],
        username = this[NetworkTraffic.username],
        timestamp = this[NetworkTraffic.timestamp]
    )



