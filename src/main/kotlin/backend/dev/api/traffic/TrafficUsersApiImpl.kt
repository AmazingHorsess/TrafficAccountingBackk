package backend.dev.api.traffic

import backend.dev.database.dao.TrafficUsersDao
import backend.dev.database.dao.tables.TrafficUsers
import backend.dev.model.PostUsernameBody
import backend.dev.model.PutUsernameInIp
import backend.dev.model.TrafficUser
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent.inject

class TrafficUsersApiImpl: TrafficUsersApi, KoinComponent {

    val trafficUsersDao by inject<TrafficUsersDao>()
    override fun getAllUsers(): List<TrafficUser> {
        return trafficUsersDao.getAllTrafficUsers()
    }

    override fun updateTrafficUsername(putUsernameBody: PutUsernameInIp) {
        return trafficUsersDao.updateTrafficUsername(putUsernameBody = putUsernameBody)
    }


}