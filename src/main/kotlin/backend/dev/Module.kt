package backend.dev

import backend.dev.api.traffic.TrafficApi
import backend.dev.modules.traffic.trafficModule
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.slf4j.event.Level

fun Application.module(){
    val trafficApi by inject<TrafficApi>()

    install(CallLogging){
        level = Level.DEBUG
    }

    install(ContentNegotiation){
        json()
    }

    install(StatusPages){
        exception<UnknownError>{ call,_ ->
            call.respondText(
                "Internal server error",
                ContentType.Text.Plain,
                status = HttpStatusCode.BadRequest
            )

        }
        exception<IllegalArgumentException> { call,_ ->
            call.respond(HttpStatusCode.BadRequest)
        }

    }

    install(Authentication){

    }

    install(Routing){
        trafficModule()

    }




}