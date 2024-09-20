package backend.dao

import backend.dao.Instrumentation.TrafficLogsTestData
import backend.dao.Instrumentation.TrafficUsersTestData
import backend.dev.database.dao.tables.TrafficLogs
import backend.dev.database.dao.tables.TrafficUsers
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.minus
import org.h2.util.DateTimeUtils.currentTimestamp
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insert
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TrafficLogDaoTest : BaseDaoTest() {


    @Test
    fun `getAllTraffic should return all logs between start and end dates`() {
        withTables(TrafficLogs, TrafficUsers) {
            TrafficUsersTestData.fill()
            TrafficLogsTestData.fill()
            val now = Clock.System.now()
            val earlier = now.minus(3600, DateTimeUnit.SECOND)
            val result = TrafficLogs.getAllTraffic(earlier, now)
            assertEquals(12, result.size)
            assertEquals("192.168.1.1", result[0].src_ip)
        }
    }

    @Test
    fun `getAllTrafficByIp should return all traffic by ip`(){
        withTables(TrafficLogs, TrafficUsers) {
            TrafficUsersTestData.fill()
            TrafficLogsTestData.fill()
            val result = TrafficLogs.getTrafficBySrcIp(sourceIp = "192.168.1.1",null,null)
            assertEquals(3, result.size)
        }
    }

    @Test
    fun `getAllTrafficByIp ip with zero logs should return zero`(){
        withTables(TrafficLogs, TrafficUsers) {
            TrafficUsersTestData.fill()
            TrafficLogsTestData.fill()
            val result = TrafficLogs.getTrafficBySrcIp(sourceIp = "192.168.1.5",null,null)
            assertEquals(0, result.size)
        }
    }



}



