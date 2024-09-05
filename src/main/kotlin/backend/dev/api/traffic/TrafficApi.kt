package backend.dev.api.traffic

import backend.dev.model.NetworkLog
import kotlinx.coroutines.flow.Flow


interface TrafficApi {
    fun getTrafficByIp(ip: String): Flow<List<NetworkLog>>
    fun setUsernameToIp(networkLogForUsernameChange: NetworkLog)
    fun getAllTrafficStats(): Flow<List<NetworkLog>>
}