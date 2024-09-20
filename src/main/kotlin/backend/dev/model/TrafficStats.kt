package backend.dev.model


import kotlinx.datetime.Instant
import kotlinx.datetime.serializers.InstantIso8601Serializer
import kotlinx.serialization.Serializable
import kotlin.String

@Serializable
data class TrafficLogs(
    val username: String?,
    val src_ip: String,
    val dst_ip: String,
    val packet_length: Long,
    val src_port: Int,
    val dst_port: Int,
    @Serializable(with = InstantIso8601Serializer::class) val ts: Instant,
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