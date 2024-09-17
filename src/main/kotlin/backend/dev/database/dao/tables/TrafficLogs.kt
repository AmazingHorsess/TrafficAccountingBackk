package backend.dev.database.dao.tables

import backend.dev.database.dao.TrafficLogsDao
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.sql.*
import backend.dev.model.TrafficLogs as ModelTrafficLogs
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.time.Duration

object TrafficLogs:Table("traffic_logs"), TrafficLogsDao {
    val src_ip = varchar("src_ip", 15).references(Users.src_ip, onDelete = ReferenceOption.CASCADE)
    val dst_ip = varchar("dst_ip", 15)
    val src_port = integer("src_port").check { it greaterEq 0 and (it lessEq 65536) }
    val dst_port = integer("dst_port").check { it greaterEq 0 and (it lessEq 65536) }
    val packet_lenght = long("packet_lenght")
    val ts = timestamp("ts")

    override fun getAllTraffic(
        startDate: Instant?,
        endDate: Instant?
    ): List<ModelTrafficLogs> {
        return transaction {
            val tz = TimeZone.UTC
            val finalStartTime = startDate?.toLocalDateTime(tz) ?: (Clock.System.now() - Duration.parse("PT5M")).toLocalDateTime(tz)
            val finalEndDate = endDate?.toLocalDateTime(tz) ?: Clock.System.now().toLocalDateTime(tz)
            (TrafficLogs innerJoin Users).selectAll().where{
                (ts greaterEq finalStartTime) and (ts lessEq finalEndDate)
            }.map { it.mapRowToTrafficLogs() }
        }
    }

    override fun getTrafficBySrcIp(
        sourceIp: String,
        startDate: Instant?,
        endDate: Instant?
    ): List<ModelTrafficLogs?> {
        return transaction {
            val tz = TimeZone.UTC
            val finalStartTime = startDate?.toLocalDateTime(tz) ?: (Clock.System.now() - Duration.parse("PT5M")).toLocalDateTime(tz)
            val finalEndDate = endDate?.toLocalDateTime(tz) ?: Clock.System.now().toLocalDateTime(tz)
            TrafficLogs.selectAll().where {
                (src_ip eq  sourceIp) and
                (ts greaterEq finalStartTime) and (ts lessEq finalEndDate)
            }.map { it.mapRowToTrafficLogs() }
        }
    }
}

fun ResultRow.mapRowToTrafficLogs() =
        ModelTrafficLogs(
            source_ip = this[TrafficLogs.src_ip],
            source_port = this[TrafficLogs.src_port],
            destination_ip = this[TrafficLogs.dst_ip],
            destination_port = this[TrafficLogs.dst_port],
            packet_length = this[TrafficLogs.packet_lenght],
            timestamp = this[TrafficLogs.ts]
        )
