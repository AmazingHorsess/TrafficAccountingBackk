package backend.dev.api.traffic

import backend.dev.model.PutUsernameInIp
import backend.dev.model.TrafficLogs
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration


interface TrafficLogApi {
    fun getTrafficByIp(sourceIp: String): Flow<List<TrafficLogs?>>
    fun getAllTraffic(
        startDate: Instant? = Clock.System.now() - Duration.parse("PT5M"),
        endDate: Instant? = Clock.System.now(),
        ): Flow<List<TrafficLogs>>
}