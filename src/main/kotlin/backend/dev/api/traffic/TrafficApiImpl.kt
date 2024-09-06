package backend.dev.api.traffic

import backend.dev.model.NetworkTraffic
import backend.dev.model.PutUsernameInIp
import backend.dev.util.JsonFileManagerContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TrafficApiImpl: TrafficApi, KoinComponent {

    val jsonManager by inject<JsonFileManagerContract>()

    override fun getTrafficByIp(sourceIp: String): Flow<List<NetworkTraffic>> = flow {
        val jsonMockData = jsonManager.readJsonFromFile(filePath)
        val trafficData: List<NetworkTraffic> = Json.decodeFromString(jsonMockData)
        val filteredData = trafficData.filter { it.source_ip == sourceIp}
        emit(filteredData)
        println(filteredData)
    }

    override fun updateUsernameInIp(sourceIp: String, putUsernameInIp: PutUsernameInIp): NetworkTraffic? {
        val jsonMockData = jsonManager.readJsonFromFile(filePath)
        var trafficData: List<NetworkTraffic> = Json.decodeFromString(jsonMockData)

        trafficData = trafficData.map { log ->
            if (log.source_ip == sourceIp) {
                log.copy(username = putUsernameInIp.username)
            } else {
                log
            }
        }

        val updatedJson = Json.encodeToString(trafficData)
        jsonManager.writeJsonToFile(filePath, updatedJson)

        return trafficData.find { it.source_ip == sourceIp }
    }

    override fun getAllTrafficStats(): Flow<List<NetworkTraffic>> = flow {
        val jsonMockData = jsonManager.readJsonFromFile(filePath)
        val trafficData: List<NetworkTraffic> = Json.decodeFromString(jsonMockData)
        emit(trafficData)
    }
    companion object {

        //:TODO Replace rigid static path binding using DI
        private val filePath = "src/main/resources/trafficmockcollection.json"

    }

}