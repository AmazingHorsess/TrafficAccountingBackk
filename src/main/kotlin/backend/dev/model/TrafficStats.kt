package backend.dev.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlin.String

@Serializable
data class NetworkTraffic(
    val source_ip: String,
    val destination_ip: String,
    val packet_length: Int,
    val source_port: Int,
    val destination_port: Int,
    val username: String?,
    val timestamp: LocalDateTime,
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