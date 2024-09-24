package backend.dev.modules.traffic

import backend.dev.model.PostUsernameBody
import backend.dev.model.PutUsernameInIp
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.toList
import org.koin.java.KoinJavaComponent.inject
import org.koin.ktor.ext.inject

fun Route.trafficUsersModule(){
    val trafficUsersController by inject<TrafficUsersController>()

    route("/traffic_users"){
        get {
            val usersFlow = trafficUsersController.getAllTrafficUsers().toList()
            println("UsersFlow: $usersFlow")  // Логирование данных

            call.respond(usersFlow.toList())
        }
        post("/traffic_user_body") {
            val postTrafficUser = call.receive<PutUsernameInIp>()
            val trafficUser = trafficUsersController.updateTrafficUsername(postTrafficUser)
            call.respond(trafficUser)


        }


    }

}