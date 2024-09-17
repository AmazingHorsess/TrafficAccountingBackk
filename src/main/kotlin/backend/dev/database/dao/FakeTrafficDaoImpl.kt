package backend.dev.database.dao

import backend.dev.model.TrafficLogs
import kotlinx.datetime.*
import kotlin.math.pow

fun formatBytes(bytes: Long): String {
    if (bytes < 1024) return "$bytes B"
    val exp = (Math.log(bytes.toDouble()) / Math.log(1024.0)).toInt()
    val pre = "KMGTPE"[exp - 1] // Kilo, Mega, Giga, etc.
    val size = bytes / 1024.0.pow(exp.toDouble())
    return String.format("%.2f %sB", size, pre)
}
class FakeTrafficDaoImpl: TrafficLogsDao {
    private val trafficData = mutableListOf<TrafficLogs>(
        TrafficLogs(
            source_ip = "192.168.1.10",
            destination_ip = "53.138.133.147",
            packet_length = 2666032,
            source_port = 8080,
            destination_port = 4545,
            username = "awoodbridge0",
            timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        ),
        TrafficLogs(source_ip = "192.168.1.10", destination_ip = "172.16.0.5", packet_length = 123456, source_port = 443, destination_port = 80, username = "user1", timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())),
        TrafficLogs(source_ip = "10.0.0.1", destination_ip = "192.168.2.20", packet_length = 654321, source_port = 22, destination_port = 21, username = "user2", timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())),
        TrafficLogs(source_ip = "172.16.5.10", destination_ip = "192.168.1.100", packet_length = 789012, source_port = 3306, destination_port = 5432, username = "user3", timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())),
        TrafficLogs(source_ip = "192.168.10.1", destination_ip = "10.0.5.2", packet_length = 345678, source_port = 80, destination_port = 443, username = "user4", timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())),
        TrafficLogs(source_ip = "10.10.1.1", destination_ip = "192.168.5.50", packet_length = 987654, source_port = 21, destination_port = 25, username = "user5", timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())),
        TrafficLogs(source_ip = "192.168.1.11", destination_ip = "172.16.0.6", packet_length = 234567, source_port = 80, destination_port = 443, username = "user6", timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())),
        TrafficLogs(source_ip = "10.0.0.2", destination_ip = "192.168.2.21", packet_length = 765432, source_port = 22, destination_port = 21, username = "user7", timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())),
        TrafficLogs(source_ip = "172.16.5.11", destination_ip = "192.168.1.101", packet_length = 890123, source_port = 3306, destination_port = 5432, username = "user8", timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())),
        TrafficLogs(source_ip = "192.168.10.2", destination_ip = "10.0.5.3", packet_length = 456789, source_port = 80, destination_port = 443, username = "user9", timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())),
        TrafficLogs(source_ip = "10.10.1.2", destination_ip = "192.168.5.51", packet_length = 876543, source_port = 21, destination_port = 25, username = "user10", timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())),
        TrafficLogs(source_ip = "192.168.1.12", destination_ip = "172.16.0.7", packet_length = 345678, source_port = 443, destination_port = 80, username = "user11", timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())),
        TrafficLogs(source_ip = "10.0.0.3", destination_ip = "192.168.2.22", packet_length = 654321, source_port = 22, destination_port = 21, username = "user12", timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())),
        TrafficLogs(source_ip = "172.16.5.12", destination_ip = "192.168.1.102", packet_length = 789012, source_port = 3306, destination_port = 5432, username = "user13", timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())),
        TrafficLogs(source_ip = "192.168.10.3", destination_ip = "10.0.5.4", packet_length = 567890, source_port = 80, destination_port = 443, username = "user14", timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())),
        TrafficLogs(source_ip = "10.10.1.3", destination_ip = "192.168.5.52", packet_length = 987654, source_port = 21, destination_port = 25, username = "user15", timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())),
        TrafficLogs(source_ip = "192.168.1.13", destination_ip = "172.16.0.8", packet_length = 234567, source_port = 80, destination_port = 443, username = "user16", timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())),
        TrafficLogs(source_ip = "10.0.0.4", destination_ip = "192.168.2.23", packet_length = 765432, source_port = 22, destination_port = 21, username = "user17", timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())),
        TrafficLogs(source_ip = "172.16.5.13", destination_ip = "192.168.1.103", packet_length = 890123, source_port = 3306, destination_port = 5432, username = "user18", timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())),
        TrafficLogs(source_ip = "192.168.10.4", destination_ip = "10.0.5.5", packet_length = 678901, source_port = 80, destination_port = 443, username = "user19", timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())),
        TrafficLogs(source_ip = "10.10.1.4", destination_ip = "192.168.5.53", packet_length = 876543, source_port = 21, destination_port = 25, username = "user20", timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())),
        TrafficLogs(source_ip = "192.168.1.14", destination_ip = "172.16.0.9", packet_length = 456789, source_port = 443, destination_port = 80, username = "user21", timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())),
        TrafficLogs(source_ip = "10.0.0.5", destination_ip = "192.168.2.24", packet_length = 654321, source_port = 22, destination_port = 21, username = "user22", timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())),
        TrafficLogs(source_ip = "172.16.5.14", destination_ip = "192.168.1.104", packet_length = 789012, source_port = 3306, destination_port = 5432, username = "user23", timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())),
        TrafficLogs(source_ip = "192.168.10.5", destination_ip = "10.0.5.6", packet_length = 789012, source_port = 80, destination_port = 443, username = "user24", timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())),
        TrafficLogs(source_ip = "10.10.1.5", destination_ip = "192.168.5.54", packet_length = 654321, source_port = 21, destination_port = 25, username = "user25", timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())),
        TrafficLogs(source_ip = "192.168.1.15", destination_ip = "172.16.0.10", packet_length = 345678, source_port = 80, destination_port = 443, username = "user26", timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())),
        TrafficLogs(source_ip = "10.0.0.6", destination_ip = "192.168.2.25", packet_length = 765432, source_port = 22, destination_port = 21, username = "user27", timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())),
        TrafficLogs(source_ip = "172.16.5.15", destination_ip = "192.168.1.105", packet_length = 890123, source_port = 3306, destination_port = 5432, username = "user28", timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())),
        TrafficLogs(source_ip = "192.168.10.6", destination_ip = "10.0.5.7", packet_length = 567890, source_port = 80, destination_port = 443, username = "user29", timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())),
    )

    override fun getAllTraffic(): List<TrafficLogs> {
        return trafficData
    }

    override fun getTrafficBySrcIp(sourceIp: String): List<TrafficLogs?> {
        return trafficData.filter { networkTraffic ->
            networkTraffic.source_ip == sourceIp
        }
    }

}