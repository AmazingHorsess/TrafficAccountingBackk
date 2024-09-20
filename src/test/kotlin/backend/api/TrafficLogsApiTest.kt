package backend.api

import backend.api.Instrumentation.TrafficLogsTestApi
import backend.dev.api.traffic.TrafficLogsApiImpl
import backend.dev.api.traffic.TrafficLogsApi
import backend.dev.database.dao.TrafficLogsDao
import backend.dev.model.TrafficLogs
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.koin.dsl.module

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TrafficLogsApiTest: BaseApiTest() {

    private val trafficLogsDao: TrafficLogsDao = mockk()
    private val api: TrafficLogsApi = TrafficLogsApiImpl()

    init {
        startInjection(
            module {
                single { trafficLogsDao }
            }
        )
    }

    @BeforeEach
    fun before(){
        clearMocks(trafficLogsDao)
    }

    @Test
    fun `when fetching all traffic logs should return logs`() = runBlocking {
        val trafficData = TrafficLogsTestApi.fill()
        every { trafficLogsDao.getAllTraffic(null, null) } returns trafficData

        val result = api.getAllTraffic(null, null).toList()

        Assertions.assertEquals(trafficData, result[0])
    }
    @Test
    fun `when fetching traffic by IP should return logs for that IP`() = runBlocking {
        val trafficData = listOf(
            TrafficLogs(
                src_ip = "192.168.1.1",
                dst_ip = "127.0.0.2",
                src_port = 8080,
                dst_port = 7070,
                packet_length = 1231231,
                username = "firstUser",
                ts = Clock.System.now()
            ),
            TrafficLogs(
                src_ip = "192.168.1.1",
                dst_ip = "127.0.0.2",
                src_port = 8080,
                dst_port = 7070,
                packet_length = 1231231,
                username = "firstUser",
                ts = Clock.System.now()
            ))
        every { trafficLogsDao.getTrafficBySrcIp("192.168.0.1", null, null) } returns trafficData

        val result = api.getTrafficByIp("192.168.0.1", null, null).toList()
        Assertions.assertEquals(trafficData, result[0])
    }

    @Test
    fun `when fetching traffic by IP that doesn't exist should return empty list`() = runBlocking {
        every { trafficLogsDao.getTrafficBySrcIp("192.168.0.5", null, null) } returns emptyList()

        val result = api.getTrafficByIp("192.168.0.5", null, null).first()
        println(result)

        Assertions.assertTrue(result.isEmpty())
    }
}