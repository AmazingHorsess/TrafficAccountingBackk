package backend.dev.model

import kotlinx.serialization.Serializable

@Serializable
data class TrafficUser(
    val src_ip: String,
    val username: String? = null,
)