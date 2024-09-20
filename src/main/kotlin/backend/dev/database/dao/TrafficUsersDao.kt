package backend.dev.database.dao

import backend.dev.model.PutUsernameInIp
import backend.dev.model.TrafficUser

interface TrafficUsersDao {
    fun getAllTrafficUsers(): List<TrafficUser>
    fun updateTrafficUsername(putUsernameBody: PutUsernameInIp)
}