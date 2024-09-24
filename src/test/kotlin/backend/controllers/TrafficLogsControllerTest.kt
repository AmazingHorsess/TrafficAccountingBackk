package backend.controllers

import backend.dev.api.traffic.TrafficLogsApi
import backend.dev.model.TrafficLogs
import backend.dev.modules.traffic.TrafficLogsController
import backend.dev.modules.traffic.TrafficLogsControllerImpl
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Instant
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.koin.dsl.module


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TrafficLogsControllerTest: BaseControllerTest() {

    private val trafficLogsApi: TrafficLogsApi = mockk()
    private val controller: TrafficLogsController by lazy { TrafficLogsControllerImpl() }

    init {
        startInjection(
            module {
                single { trafficLogsApi }

            }
        )
    }

    @BeforeEach
    override fun before() {
        super.before()
        clearMocks(trafficLogsApi)
    }

    @Test
    fun `getAllTrafficStats should return data when valid start and end dates are passed`() = runBlocking {
        // Given
        val startDate = Instant.parse("2023-01-01T00:00:00Z")
        val endDate = Instant.parse("2023-01-01T23:59:59Z")
        val trafficLogs = listOf(mockk<TrafficLogs>())
        coEvery { trafficLogsApi.getAllTraffic(startDate, endDate) } returns flowOf(trafficLogs)

        // When
        val result = controller.getAllTrafficStats(startDate, endDate).toList()

        // Then
        coVerify { trafficLogsApi.getAllTraffic(startDate, endDate) }
        assertEquals(1, result.size)
        assertEquals(trafficLogs, result[0])
    }

    @Test
    fun `getAllTrafficStats should handle null dates and default to the last 5 minutes`() = runBlocking {
        // Given
        val trafficLogs = listOf(mockk<TrafficLogs>())
        coEvery { trafficLogsApi.getAllTraffic(any(), any()) } returns flowOf(trafficLogs)

        // When
        val result = controller.getAllTrafficStats(null, null).toList()

        // Then
        coVerify { trafficLogsApi.getAllTraffic(any(), any()) }
        assertEquals(1, result.size)
        assertEquals(trafficLogs, result[0])
    }


}