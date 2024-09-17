package backend.dev.modules.traffic

import backend.dev.api.traffic.TrafficLogApi
import backend.dev.database.dao.TrafficLogsDao
import backend.dev.model.TrafficLogs
import backend.dev.model.PutUsernameInIp
import backend.dev.modules.BaseController
import backend.dev.statuspages.InvalidIpException
import backend.dev.util.Qualifiers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FakeTrafficController: BaseController(),TrafficController, KoinComponent {
    private val trafficDao by inject<TrafficLogsDao>(Qualifiers.devQualifier)
    private val trafficApi by inject<TrafficLogApi>(Qualifiers.devQualifier)
    override suspend fun changeUsername(putUsernameInIp: PutUsernameInIp): TrafficLogs? {
        return dbQuery {
            try {
                val updatedName = trafficApi.updateUsernameInIp(putUsernameInIp)
                updatedName ?: throw InvalidIpException("The IP address is invalid or not found.")
            } catch (e: Exception) {
                throw UnknownError("Internal server error")
            }
        }
    }

    override suspend fun getTrafficByIp(sourceIp: String): Flow<List<TrafficLogs>> = dbQuery {
        try {
            trafficApi.getTrafficByIp(sourceIp)
                .map { trafficList ->
                    trafficList.filterNotNull()
                }
        } catch (e: Exception) {
            throw UnknownError("Internal server error")
        }
    }

    override suspend fun getAllTrafficStats(): Flow<List<TrafficLogs>> {
        return trafficApi.getAllTraffic()
    }


}