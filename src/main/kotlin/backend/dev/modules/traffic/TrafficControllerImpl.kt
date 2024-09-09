package backend.dev.modules.traffic

import backend.dev.api.traffic.TrafficApi
import backend.dev.model.NetworkTraffic
import backend.dev.model.PutUsernameInIp
import backend.dev.modules.BaseController
import backend.dev.statuspages.InvalidIpException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TrafficControllerImpl: BaseController(),TrafficController,KoinComponent {

    private val trafficApi by inject<TrafficApi>()

    override suspend fun changeUsername(putUsernameInIp: PutUsernameInIp): NetworkTraffic {
        return dbQuery {
            try {
                val updatedName = trafficApi.updateUsernameInIp(putUsernameInIp)
                updatedName ?: throw InvalidIpException("The IP address is invalid or not found.")
            } catch (e: Exception) {
                throw UnknownError("Internal server error")
            }
        }
    }

    override suspend fun getTrafficByIp(sourceIp: String): Flow<List<NetworkTraffic>> = dbQuery {
        try {
            trafficApi.getTrafficByIp(sourceIp)
                .map { trafficList ->
                    trafficList.filterNotNull()
                }
        } catch (e: Exception) {
            throw UnknownError("Internal server error")
        }
    }

    override suspend fun getAllTrafficStats(): Flow<List<NetworkTraffic>> {
        return trafficApi.getAllTraffic()
    }
}