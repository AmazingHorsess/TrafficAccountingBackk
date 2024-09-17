package backend.dev.modules.traffic

import backend.dev.model.TrafficLogs
import backend.dev.model.PutUsernameInIp
import kotlinx.coroutines.flow.Flow

interface TrafficController {
    suspend fun changeUsername(putUsernameInIp: PutUsernameInIp): TrafficLogs?
    suspend fun getTrafficByIp(sourceIp: String): Flow<List<TrafficLogs>>
    suspend fun getAllTrafficStats(): Flow<List<TrafficLogs>>
}