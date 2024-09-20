package backend.dev.database.dao

import backend.dev.model.TrafficLogs
import kotlinx.datetime.*
import kotlin.time.Duration

interface TrafficLogsDao {

    fun getAllTraffic(
        startDate: Instant?,
        endDate: Instant?,
    ): List<TrafficLogs>

    fun getTrafficBySrcIp(
        sourceIp: String,
        startDate: Instant?,
        endDate: Instant?,
    ): List<TrafficLogs?>
}