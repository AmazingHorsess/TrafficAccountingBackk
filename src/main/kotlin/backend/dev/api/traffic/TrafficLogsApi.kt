package backend.dev.api.traffic

import backend.dev.model.TrafficLogs
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration


interface TrafficLogsApi {
    fun getTrafficByIp(
        sourceIp: String,
        startDate: Instant?,
        endDate: Instant?,

    ): Flow<List<TrafficLogs?>>

    fun getAllTraffic(
        startDate: Instant?,
        endDate: Instant?,
        ): Flow<List<TrafficLogs>>
}