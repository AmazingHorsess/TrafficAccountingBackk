package backend.dev.model

data class TrafficUser(
    val src_ip: String,
    val username: String? = null,
)