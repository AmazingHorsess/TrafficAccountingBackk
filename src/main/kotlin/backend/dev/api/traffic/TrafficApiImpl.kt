package backend.dev.api.traffic

import backend.dev.database.dao.TrafficDao
import backend.dev.model.NetworkTraffic
import backend.dev.model.PutUsernameInIp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TrafficApiImpl: TrafficApi, KoinComponent {
    private val trafficDao by inject<TrafficDao>()

    override fun getTrafficByIp(sourceIp: String): Flow<List<NetworkTraffic?>> = flow {
        val trafficData = trafficDao.getTrafficByIp(sourceIp)
        emit(trafficData)
    }

    override fun updateUsernameInIp(putUsernameInIp: PutUsernameInIp): NetworkTraffic? {
        return trafficDao.updateUsernameOnIp(PutUsernameInIp(putUsernameInIp.source_ip, putUsernameInIp.username))
    }

    override fun getAllTraffic(): Flow<List<NetworkTraffic>> = flow {
        val trafficData = trafficDao.getAllTraffic()
        emit(trafficData)

    }

}