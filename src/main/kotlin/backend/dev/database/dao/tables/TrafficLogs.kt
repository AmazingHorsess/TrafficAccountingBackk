package backend.dev.database.dao.tables

    import backend.dev.database.dao.TrafficLogsDao
    import backend.dev.util.DateFilterService
    import kotlinx.datetime.Instant
    import org.jetbrains.exposed.sql.*
    import backend.dev.model.TrafficLogs as ModelTrafficLogs
    import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
    import org.jetbrains.exposed.sql.transactions.transaction
    import org.koin.core.component.KoinComponent
    import org.koin.core.component.inject

object TrafficLogs : Table("traffic_logs"), TrafficLogsDao,KoinComponent {
    private val dateFilter by inject<DateFilterService>()
    val src_ip = varchar("src_ip", 15).references(TrafficUsers.src_ip, onDelete = ReferenceOption.CASCADE)
    val dst_ip = varchar("dst_ip", 15)
    val src_port = integer("src_port").check { it greaterEq 0 and (it lessEq 65536) }
    val dst_port = integer("dst_port").check { it greaterEq 0 and (it lessEq 65536) }
    val packet_length = long("packet_length")
    val ts = timestamp("ts")

    override fun getAllTraffic(
        startDate: Instant?,
        endDate: Instant?
    ): List<ModelTrafficLogs> {
        return transaction {
            val query = TrafficLogs.leftJoin(TrafficUsers).selectAll()
            dateFilter.applySqlDateFilter(query, startDate, endDate)
            query.map { it.mapRowToTrafficLogs() }
        }
    }

    override fun getTrafficBySrcIp(
        sourceIp: String,
        startDate: Instant?,
        endDate: Instant?
    ): List<ModelTrafficLogs?> {
        return transaction {
            val query = TrafficLogs.leftJoin(TrafficUsers).selectAll()
            query.andWhere { src_ip eq sourceIp }
            dateFilter.applySqlDateFilter(query, startDate, endDate)
            query.map { it.mapRowToTrafficLogs() }
        }
    }

    private fun ResultRow.mapRowToTrafficLogs() =
        ModelTrafficLogs(
            username = this[TrafficUsers.username],
            src_ip = this[TrafficLogs.src_ip],
            src_port = this[TrafficLogs.src_port],
            dst_ip = this[TrafficLogs.dst_ip],
            dst_port = this[TrafficLogs.dst_port],
            packet_length = this[TrafficLogs.packet_length],
            ts = this[TrafficLogs.ts]
        )
}
