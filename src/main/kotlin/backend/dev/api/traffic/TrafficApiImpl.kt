package backend.dev.api.traffic

import backend.dev.model.NetworkLog
import backend.dev.util.JsonFileManagerContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TrafficApiImpl: TrafficApi, KoinComponent {

    val jsonManager by inject<JsonFileManagerContract>()

    override fun getTrafficByIp(ip: String): Flow<List<NetworkLog>> = flow {
        val jsonMockData = jsonManager.readJsonFromFile(filePath)
        val trafficData: List<NetworkLog> = Json.decodeFromString(jsonMockData)
        val filteredData = trafficData.filter { it.source_ip == ip}
        emit(filteredData)
        println(filteredData)
    }

    override fun setUsernameToIp(networkLogForUsernameChange: NetworkLog) {
        val jsonMockData = jsonManager.readJsonFromFile(filePath)
        var trafficData: List<NetworkLog> = Json.decodeFromString(jsonMockData)

        trafficData = trafficData.map { log ->
            if (log.source_ip == networkLogForUsernameChange.source_ip){
                log.copy(username = networkLogForUsernameChange.username)
            } else {
                log
            }

        }

        val updatedJson = Json.encodeToString(trafficData)

        jsonManager.writeJsonToFile(filePath, updatedJson)
    }

    override fun getAllTrafficStats(): Flow<List<NetworkLog>> = flow {
        val jsonMockData = jsonManager.readJsonFromFile(filePath)
        val trafficData: List<NetworkLog> = Json.decodeFromString(jsonMockData)
        emit(trafficData)
    }
    companion object {

        //:TODO Replace rigid static path binding using DI
        private val filePath = "src/main/resources/trafficmockcollection.json"

    }

}