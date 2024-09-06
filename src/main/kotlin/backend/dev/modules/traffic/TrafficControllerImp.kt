package backend.dev.modules.traffic

import backend.dev.api.traffic.TrafficApi
import backend.dev.model.NetworkTraffic
import backend.dev.model.PutUsernameInIp
import backend.dev.modules.BaseController
import backend.dev.statuspages.InvalidIpException
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TrafficControllerImp: BaseController(),TrafficController,KoinComponent {

    private val trafficApi by inject<TrafficApi>()

    override suspend fun changeUsername(sourceIp:String, putUsernameInIp: PutUsernameInIp) = dbQuery {
        val updatedTraffic = trafficApi.updateUsernameInIp(sourceIp, putUsernameInIp)
        updatedTraffic ?: throw InvalidIpException()
    }
    override suspend fun getTrafficByIp(sourceIp: String): Flow<List<NetworkTraffic>> = dbQuery {
        trafficApi.getTrafficByIp(sourceIp)
    }

    override suspend fun getAllTrafficStats(): Flow<List<NetworkTraffic>> {
        trafficApi.getAllTrafficStats()
    }


}