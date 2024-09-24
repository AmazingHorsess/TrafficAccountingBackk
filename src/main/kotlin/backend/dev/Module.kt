package backend.dev

import backend.dev.database.DatabaseProvider
import backend.dev.modules.traffic.trafficLogsModule
import backend.dev.modules.traffic.trafficUsersModule
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
    val databaseProvider by inject<DatabaseProvider>()
    databaseProvider.init()

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
        trafficLogsModule()
        trafficUsersModule()

    }




}