package backend.dev

import backend.dev.api.traffic.TrafficApiImpl
import backend.dev.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    val api = TrafficApiImpl()


    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
    api.getAllTrafficStats()
    api.getTrafficByIp("192.198.1.1")
}

fun Application.module() {
    configureSecurity()
    configureMonitoring()
    configureSerialization()
    configureDatabases()
    configureFrameworks()
    configureRouting()
}
