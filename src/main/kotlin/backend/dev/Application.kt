package backend.dev

import backend.dev.api.traffic.TrafficApiImpl
import backend.dev.api.traffic.injection.ApiInjection
import backend.dev.modules.injection.ModulesInjection
import backend.dev.plugins.*
import backend.dev.util.JsonFileManager
import backend.dev.util.JsonFileManagerContract
import io.ktor.client.plugins.BodyProgress.Plugin.install
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

fun main() {
    val api = TrafficApiImpl()

    val environment = System.getenv()["ENVIRONMENT"] ?: handleDefaultEnvironment()



    embeddedServer(Netty, port = 8080, host = "0.0.0.0"){
        module{
            install(Koin){
                modules(
                    module{
                        single<JsonFileManagerContract>{JsonFileManager}


                    },
                    ApiInjection.koinBeans,
                    ModulesInjection.koinBeans,
                )
            }
            main()
        }

    }.start(wait = true)
    api.getAllTrafficStats()
    api.getTrafficByIp("192.198.1.1")
}

fun Application.main() {
    module()
}

fun handleDefaultEnvironment(): String {
    println("Falling back to default environment 'dev'")
    return "dev"
}
