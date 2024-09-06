package backend.dev.modules.traffic

import backend.dev.model.NetworkTraffic
import backend.dev.model.PutUsernameInIp
import kotlinx.coroutines.flow.Flow

interface TrafficController {
    suspend fun changeUsername(sourceIp: String, putUsernameInIp: PutUsernameInIp): NetworkTraffic?
    suspend fun getTrafficByIp(sourceIp: String): Flow<List<NetworkTraffic>>
    suspend fun getAllTrafficStats(): Flow<List<NetworkTraffic>>
}