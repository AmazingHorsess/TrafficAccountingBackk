package backend.dev.api.traffic

import backend.dev.database.dao.tables.TrafficUsers
import backend.dev.model.PostUsernameBody
import backend.dev.model.PutUsernameInIp
import backend.dev.model.TrafficUser
import kotlinx.coroutines.flow.Flow

interface TrafficUsersApi {

    fun getAllUsers(): Flow<List<TrafficUser>>

    fun updateTrafficUsername(putUsernameBody: PutUsernameInIp)


}