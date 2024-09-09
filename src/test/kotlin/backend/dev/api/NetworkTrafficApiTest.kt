package backend.dev.api

import backend.dev.api.traffic.TrafficApiImpl
import backend.dev.model.NetworkTraffic
import backend.dev.util.JsonFileManager
import io.mockk.*
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test

//class NetworkTrafficApiTest: BaseApiTest() {
//
//    private val api: TrafficApiImpl = TrafficApiImpl()
//
//
//    private lateinit var trafficApi: TrafficApiImpl
//
//    private var jsonManager = JsonFileManager
//
//    @Before
//    fun setUp() {
//        trafficApi = TrafficApiImpl()
//        val initialData = listOf(
//            NetworkTraffic("192.168.1.10", "192.168.1.20", 1024, 1024, 8080,username = null,"2024-09-05T10:15:30Z"),
//            NetworkTraffic("192.168.1.11", "192.168.1.21", 2048, 2048, 7070, username = "Device1","2024-09-05T10:15:30Z")
//        )
//        val jsonData = Json.encodeToString(initialData)
//    }
//
//    @Test
//    fun testGetTrafficByIp() = runTest{
//        val updatedLog = NetworkTraffic("192.168.1.10", "192.168.1.20", 6000, 1024, 10, "user1","2024-09-05T10:15:30Z")
//        val updatedData = listOf(
//            updatedLog,
//            NetworkTraffic("192.168.1.11", "192.168.1.21", 1024, 2048, 7070, username = null,"2024-09-05T10:15:30Z"),
//        )
//        val updatedJson = Json.encodeToString(updatedData)
//
//
//
//        // Настройка поведения мока для записи
//        every { jsonManager.writeJsonToFile(any(), any()) } just Runs
//
//        // Выполняем метод
//        trafficApi.updateUsernameInIp(updatedLog)
//
//        // Проверяем, что запись в файл была вызвана с обновленными данными
//        verify { jsonManager.writeJsonToFile("src/main/resources/trafficmockcollection.json", updatedJson) }
//    }
//
//    @Test
//    fun testGetAllTrafficStats() = runTest {
//        val api = spyk(trafficApi) // Используем spyk для частичного мока
//
//        val result = api.getAllTraffic().toList()
//        assertTrue(result.isNotEmpty())
//        assertEquals(2, result[0].size) // Проверка на количество начальных элементов
//    }
//}