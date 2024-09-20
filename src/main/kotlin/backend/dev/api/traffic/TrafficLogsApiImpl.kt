package backend.dev.api.traffic

import backend.dev.database.dao.TrafficLogsDao
import backend.dev.model.TrafficLogs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Instant
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TrafficLogsApiImpl: TrafficLogsApi, KoinComponent {

    private val trafficLogsDao by inject<TrafficLogsDao>()

    override fun getAllTraffic(startDate: Instant?, endDate: Instant?): Flow<List<TrafficLogs>> {
        return flow {
            val trafficData = trafficLogsDao.getAllTraffic(startDate,endDate)
                emit(trafficData)

        }
    }

    override fun getTrafficByIp(
        sourceIp: String,
        startDate: Instant?,
        endDate: Instant?,
    ): Flow<List<TrafficLogs?>> {
        return flow {
            val trafficData = trafficLogsDao.getTrafficBySrcIp(
                sourceIp,
                startDate,
                endDate,
            )
            emit(trafficData)
        }
    }

}