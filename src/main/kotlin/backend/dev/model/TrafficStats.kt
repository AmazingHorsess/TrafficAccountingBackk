package backend.dev.model

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
    val timestamp: String,
)

@Serializable
data class PutUsernameInIp(
    val source_ip: String,
    val username: String,
)
