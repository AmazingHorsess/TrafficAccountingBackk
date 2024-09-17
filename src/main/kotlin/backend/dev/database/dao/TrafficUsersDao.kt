package backend.dev.database.dao

import backend.dev.model.PostUsernameBody
import backend.dev.model.PutUsernameInIp
import backend.dev.model.TrafficUser

interface TrafficUsersDao {
    fun insertUser(putUsernameBody: PutUsernameInIp)
    fun getAllUsers(): List<TrafficUser>
    fun updateUsername(putUsernameBody: PutUsernameInIp)
}