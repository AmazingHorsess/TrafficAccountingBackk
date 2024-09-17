package backend.dev.database.dao

import backend.dev.model.TrafficLogs
import kotlinx.datetime.*
import kotlin.time.Duration

interface TrafficLogsDao {
    fun getAllTraffic(
        startDate: Instant? = Clock.System.now() - Duration.parse("PT5M"),
        endDate: Instant? = Clock.System.now(),
    ): List<TrafficLogs>

    fun getTrafficBySrcIp(
        sourceIp: String,
        startDate: Instant? = Clock.System.now() - Duration.parse("PT5M"),
        endDate: Instant? = Clock.System.now()
    ): List<TrafficLogs?>
}