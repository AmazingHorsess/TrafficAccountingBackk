package backend.dev.modules.traffic

import backend.dev.model.PutUsernameInIp
import backend.dev.model.TrafficUser
import kotlinx.coroutines.flow.Flow

interface TrafficUsersController {
    suspend fun getAllTrafficUsers(): Flow<List<TrafficUser>>
    suspend fun updateTrafficUsername(putUsernameBody: PutUsernameInIp)
}