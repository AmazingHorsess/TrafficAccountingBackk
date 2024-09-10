package backend.dev.modules.traffic

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.toList
import org.koin.ktor.ext.inject

fun Route.trafficModule(){
    val controller by inject<TrafficController>()

    route("/traffic"){
        get{
            runCatching {
                controller.getAllTrafficStats()
            }.fold(
                onSuccess = { allTraffic ->
                    call.respond(HttpStatusCode.OK,allTraffic)
                },
                onFailure = {
                    call.respond(HttpStatusCode.InternalServerError, it.message?:"Unknown error")
                }
            )
        }
    }
}