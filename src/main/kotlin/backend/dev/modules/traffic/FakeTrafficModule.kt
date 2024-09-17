package backend.dev.modules.traffic

import backend.dev.model.PutUsernameInIp
import backend.dev.util.Qualifiers
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.fakeTrafficModule() {
    val controller by inject<TrafficController>(Qualifiers.devQualifier)

    route("/traffic") {

        // Получение всех данных о трафике
        get {
            runCatching {
                controller.getAllTrafficStats()
            }.fold(
                onSuccess = { allTraffic ->
                    call.respond(HttpStatusCode.OK, allTraffic)
                },
                onFailure = { exception ->
                    call.respond(HttpStatusCode.InternalServerError, exception.message ?: "Unknown error")
                }
            )
        }
        get("/{sourceIp}") {
            val sourceIp = call.parameters["sourceIp"] ?: return@get call.respond(
                HttpStatusCode.BadRequest, "Missing or malformed IP address"
            )

            runCatching {
                controller.getTrafficByIp(sourceIp)  // Преобразуем Flow в List
            }.fold(
                onSuccess = { trafficByIp ->
                    call.respond(HttpStatusCode.OK, trafficByIp)
                },
                onFailure = { exception ->
                    call.respond(HttpStatusCode.InternalServerError, exception.message ?: "Unknown error")
                }
            )
        }

        // Изменение имени пользователя по IP-адресу
        put("/update-username") {
            val putUsernameInIp = runCatching { call.receive<PutUsernameInIp>() }.getOrElse {
                return@put call.respond(HttpStatusCode.BadRequest, "Invalid request body")
            }

            runCatching {
                controller.changeUsername(putUsernameInIp)
            }.fold(
                onSuccess = { updatedTraffic ->
                    updatedTraffic?.let {
                        call.respond(HttpStatusCode.OK, it)
                    } ?: call.respond(HttpStatusCode.NotFound, "IP address not found")
                },
                onFailure = { exception ->
                    call.respond(HttpStatusCode.InternalServerError, exception.message ?: "Unknown error")
                }
            )
        }
    }
}