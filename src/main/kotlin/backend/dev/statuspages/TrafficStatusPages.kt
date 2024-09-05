package backend.dev.statuspages

import io.ktor.http.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun StatusPagesConfig.trafficStatusPages() {
    exception<InvalidIpException>{ call, cause ->
        call.respond(HttpStatusCode.BadRequest, cause.localizedMessage)
    }
}

data class InvalidIpException(override val message: String = "Invalid Ip") : Exception()