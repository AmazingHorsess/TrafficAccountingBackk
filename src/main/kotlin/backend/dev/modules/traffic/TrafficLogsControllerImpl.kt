package backend.dev.modules.traffic

import backend.dev.api.traffic.TrafficLogsApi
import backend.dev.model.TrafficLogs
import backend.dev.modules.BaseController
import backend.dev.util.DateFilterService
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.time.Duration

class TrafficLogsControllerImpl: BaseController(), TrafficLogsController, KoinComponent{
    private val trafficLogsApi by inject<TrafficLogsApi>()
    private val dateFilter by inject<DateFilterService>()


    override suspend fun getAllTrafficStats(startDate: Instant?, endDate: Instant?): Flow<List<TrafficLogs>> {
        return dbQuery {
            val (finalStartDate, finalEndDate) = dateFilter.processDates(startDate, endDate)
            trafficLogsApi.getAllTraffic(finalStartDate, finalEndDate)

        }
    }

    override suspend fun getTrafficByIp(
        sourceIp: String,
        startDate: Instant?,
        endDate: Instant?
    ): Flow<List<TrafficLogs?>> {
        return dbQuery {
            val (finalStartDate, finalEndDate) = dateFilter.processDates(startDate, endDate)
            trafficLogsApi.getTrafficByIp(
                sourceIp,
                finalStartDate,
                finalEndDate,
            )
        }
    }
}
