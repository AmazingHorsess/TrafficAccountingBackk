package backend.dev.api.traffic

import backend.dev.database.dao.TrafficUsersDao
import backend.dev.model.PutUsernameInIp
import backend.dev.model.TrafficUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class TrafficUsersApiImpl: TrafficUsersApi, KoinComponent {

    private val trafficUsersDao by inject<TrafficUsersDao>()
    override fun getAllUsers(): Flow<List<TrafficUser>> {
        return flow{
            emit(trafficUsersDao.getAllTrafficUsers())
        }
    }
                    
    override fun updateTrafficUsername(putUsernameBody: PutUsernameInIp) {
        return trafficUsersDao.updateTrafficUsername(putUsernameBody = putUsernameBody)
    }


}