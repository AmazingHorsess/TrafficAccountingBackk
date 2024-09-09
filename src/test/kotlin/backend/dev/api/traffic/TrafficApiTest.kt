package backend.dev.api.traffic

import backend.dev.api.BaseApiTest
import backend.dev.controllers.instrumentation.TrafficModuleInstrumentation
import backend.dev.database.dao.TrafficDao
import backend.dev.model.NetworkTraffic
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
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
    fun `when fetching by ip will return data`() = runBlocking{
        val data = TrafficModuleInstrumentation.givenTraffic()
        val expectedData = data.filter { it.source_ip == "192.168.1.11" }
        val flowData: Flow<List<NetworkTraffic>> = flowOf(expectedData)

        every { trafficDao.getTrafficByIp("192.168.1.11") } returns data

        val result = api.getTrafficByIp("192.168.1.11")

        assertEquals(expectedData, result)
        verify { trafficDao.getTrafficByIp("192.168.1.11") }




    }
}