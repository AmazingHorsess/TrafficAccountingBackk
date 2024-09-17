package backend.dev.model


import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlin.String

@Serializable
data class TrafficLogs(
    val source_ip: String,
    val destination_ip: String,
    val packet_length: Long,
    val source_port: Int,
    val destination_port: Int,
    val timestamp: Instant,
)

@Serializable
data class PutUsernameInIp(
    val source_ip: String,
    val username: String,
)

@Serializable
data class PostUsernameBody(
    val source_ip: String,
    val username: String,
)