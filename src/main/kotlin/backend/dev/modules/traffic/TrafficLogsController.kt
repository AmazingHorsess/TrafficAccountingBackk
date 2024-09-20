package backend.dev.modules.traffic

import backend.dev.model.TrafficLogs
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration

interface TrafficLogsController {
    suspend fun getTrafficByIp(
        sourceIp: String,
        startDate: Instant?,
        endDate: Instant?,
    ): Flow<List<TrafficLogs?>>
    suspend fun getAllTrafficStats(
        startDate: Instant?,
        endDate: Instant?,
    ): Flow<List<TrafficLogs>>
}