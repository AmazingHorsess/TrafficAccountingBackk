package backend.dev.controllers.instrumentation

import backend.dev.model.NetworkTraffic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter

object TrafficModuleInstrumentation {
    private val networkTrafficList = listOf(
        NetworkTraffic(
            source_ip = "192.168.1.10",
            destination_ip = "192.168.1.20",
            packet_length = 1024,
            source_port = 80,
            destination_port = 8080,
            username = "user1",
            timestamp = LocalDateTime.parse("2024-09-05T10:15:30")
        ),
        NetworkTraffic(
            source_ip = "192.168.1.11",
            destination_ip = "192.168.1.21",
            packet_length = 2048,
            source_port = 443,
            destination_port = 8443,
            username = "user2",
            timestamp = LocalDateTime.parse("2024-09-05T10:16:30")
        ),
        NetworkTraffic(
            source_ip = "192.168.1.11",
            destination_ip = "192.168.1.21",
            packet_length = 20428,
            source_port = 443,
            destination_port = 8443,
            username = "user2",
            timestamp = LocalDateTime.parse("2024-09-05T10:16:30")
        ),
        NetworkTraffic(
            source_ip = "192.168.1.12",
            destination_ip = "192.168.1.22",
            packet_length = 512,
            source_port = 22,
            destination_port = 2222,
            username = "user3",
            timestamp = LocalDateTime.parse("2024-09-05T10:17:30")
        )

    )

    fun givenTraffic(): List<NetworkTraffic> = networkTrafficList




}