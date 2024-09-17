package backend.dev.api.traffic

import backend.dev.database.dao.TrafficLogsDao
import backend.dev.model.TrafficLogs
import backend.dev.model.PutUsernameInIp
import backend.dev.util.Qualifiers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FakeTrafficApiImpl:TrafficLogApi, KoinComponent {
    private val trafficDao by inject<TrafficLogsDao>(qualifier = Qualifiers.devQualifier)


    override fun getTrafficByIp(sourceIp: String): Flow<List<TrafficLogs?>> {
        return flow {
            emit(trafficDao.getTrafficBySrcIp(sourceIp))
        }
    }

    override fun updateUsernameInIp(putUsernameInIp: PutUsernameInIp): TrafficLogs? {
        return trafficDao.updateUsernameOnIp(putUsernameInIp)
    }

    override fun getAllTraffic(): Flow<List<TrafficLogs>> {
        return flow {
            emit(trafficDao.getAllTraffic())
        }
    }

}