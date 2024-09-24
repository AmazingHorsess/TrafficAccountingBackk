package backend.dev.modules.traffic

import backend.dev.api.traffic.TrafficUsersApi
import backend.dev.model.PutUsernameInIp
import backend.dev.model.TrafficUser
import backend.dev.modules.BaseController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent.inject

class TrafficUsersControllerImpl: BaseController(),TrafficUsersController, KoinComponent {
    private val trafficUsersApi by inject<TrafficUsersApi>()
    override suspend fun getAllTrafficUsers(): Flow<List<TrafficUser>> {
        return dbQuery {
            trafficUsersApi.getAllUsers()
        }
    }

    override suspend fun updateTrafficUsername(putUsernameBody: PutUsernameInIp) {
        return dbQuery {
            trafficUsersApi.updateTrafficUsername(putUsernameBody)
        }
    }

}