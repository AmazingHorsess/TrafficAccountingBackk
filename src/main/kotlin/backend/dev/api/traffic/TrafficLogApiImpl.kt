package backend.dev.api.traffic

import backend.dev.database.dao.TrafficLogsDao
import backend.dev.model.TrafficLogs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Instant
import org.koin.java.KoinJavaComponent.inject

class TrafficLogApiImpl: TrafficLogApi {

    private val trafficLogsDao by inject<TrafficLogsDao>()

    override fun getAllTraffic(startDate: Instant?, endDate: Instant?): Flow<List<TrafficLogs>> {
        return flow {
            trafficLogsDao.getAllTraffic(startDate,endDate)
        }
    }
}