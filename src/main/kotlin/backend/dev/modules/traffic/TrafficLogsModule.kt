package backend.dev.modules.traffic

import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.coroutines.flow.toList
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.koin.ktor.ext.inject

fun Route.trafficLogsModule() {
    val trafficLogsController by inject<TrafficLogsController>()


    route("/traffic_logs") {
        get {
            val startDate = call.request.queryParameters["startDate"]?.let { LocalDateTime.parse(it) }
            val endDate = call.request.queryParameters["endDate"]?.let { LocalDateTime.parse(it) }

            val logsFlow = trafficLogsController.getAllTrafficStats(
                startDate?.toInstant(TimeZone.UTC),
                endDate?.toInstant(TimeZone.UTC)
            ).toList()
            call.respond(logsFlow)
        }

        get("/{sourceIp}"){
            val sourceIp = call.parameters["sourceIp"]
            val startDate = call.request.queryParameters["startDate"]?.let { LocalDateTime.parse(it) }
            val endDate = call.request.queryParameters["endDate"]?.let { LocalDateTime.parse(it) }
            if (sourceIp == null) {
                call.respond(HttpStatusCode.BadRequest, "Source IP is required")
                return@get
            }
            val logsFlow = trafficLogsController.getTrafficByIp(
                sourceIp,
                startDate?.toInstant(TimeZone.UTC),
                endDate?.toInstant(TimeZone.UTC)
            ).toList()
            call.respond(logsFlow)
        }

    }

}