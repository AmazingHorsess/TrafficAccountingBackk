package backend.dev.plugins

import backend.dev.api.traffic.TrafficApiImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.withContext
import kotlinx.html.*

fun Application.configureRouting() {
    val api = TrafficApiImpl()

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
    routing {



        get("/") {
            val trafficData = withContext(Dispatchers.IO) {
                api.getAllTraffic().toList()
            }
            delay(600)


            call.respondHtml(HttpStatusCode.OK) {
                head {
                    title { +"Traffic Stats" }
                }
                body {
                    h1 { +"Traffic Stats" }
                    table {
                        tr {
                            th { +"Source IP" }
                            th { +"Destination IP" }
                            th { +"Timestamp" }
                            th { +"Bytes" }
                            th { +"Packets" }
                            th { +"Username" }
                        }
                        for (log in trafficData.flatten()) {
                            tr {
                                td { +log.source_ip }
                                td { +log.destination_ip }
                                td { +log.timestamp }
                                td { +log.packet_length.toString() }
                                td { +log.username!! ?: "N/A" }
                            }
                        }
                    }
                }
            }

        }
       

    }
}

