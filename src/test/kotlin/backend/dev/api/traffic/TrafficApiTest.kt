package backend.dev.api.traffic

import backend.dev.api.BaseApiTest
import backend.dev.controllers.instrumentation.TrafficModuleInstrumentation
import backend.dev.database.dao.TrafficDao
import backend.dev.model.NetworkTraffic
import backend.dev.model.PutUsernameInIp
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.koin.dsl.module

import kotlin.test.Test
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TrafficApiTest : BaseApiTest(){

    private val trafficDao: TrafficDao = mockk()
    private val api: TrafficApi = TrafficApiImpl()

    init {
        startInjection(
            module {
                single{ trafficDao }
            }
        )
    }

    @BeforeEach
    fun before() {
        clearMocks(trafficDao)
    }

    @Test
    fun `when fetching network traffic we return data`() = runBlocking{
        val data = TrafficModuleInstrumentation.givenTraffic()
        every { trafficDao.getAllTraffic() } returns data

        val result = api.getAllTraffic().first()
        assertEquals(data,result)
        verify { trafficDao.getAllTraffic() }
    }

    @Test
    fun `when fetching by ip will return data`() = runBlocking {
        val data = TrafficModuleInstrumentation.givenTraffic()
        val expectedData = data.filter { it.source_ip == "192.168.1.11" }
        every { trafficDao.getTrafficByIp("192.168.1.11") } returns expectedData

        val result = api.getTrafficByIp("192.168.1.11").first()
        assertEquals(expectedData, result)
        verify { trafficDao.getTrafficByIp("192.168.1.11") }
    }

    @Test
    fun `when change username on ip will return new username`() = runBlocking{
        val data = TrafficModuleInstrumentation.givenTraffic()
        println("Data: $data")

        val targetIp = "192.168.1.11"
        val newUsername = "newUser"
        val putUsernameInIp = PutUsernameInIp(targetIp, newUsername)


        val updatedTraffic = data.map {
            if (it.source_ip == targetIp) it.copy(username = newUsername) else it
        }.firstOrNull { it.source_ip == targetIp }
        every { trafficDao.updateUsernameOnIp(putUsernameInIp) } returns updatedTraffic

        every { trafficDao.getTrafficByIp(targetIp) } returns listOf(updatedTraffic).filterNotNull()

        val result = api.updateUsernameInIp(putUsernameInIp)
        verify { trafficDao.updateUsernameOnIp(putUsernameInIp) }
        assertEquals(updatedTraffic, result)


    }
}