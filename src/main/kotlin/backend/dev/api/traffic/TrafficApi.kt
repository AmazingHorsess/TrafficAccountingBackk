package backend.dev.api.traffic

import backend.dev.model.PutUsernameInIp
import backend.dev.model.NetworkTraffic
import kotlinx.coroutines.flow.Flow


interface TrafficApi {
    fun getTrafficByIp(sourceIp: String): Flow<List<NetworkTraffic?>>
    fun updateUsernameInIp(putUsernameInIp: PutUsernameInIp): NetworkTraffic?
    fun getAllTraffic(): Flow<List<NetworkTraffic>>
}