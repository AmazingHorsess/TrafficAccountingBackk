package backend.dev

import backend.dev.api.traffic.TrafficApiImpl
import backend.dev.api.traffic.injection.ApiInjection
import backend.dev.config.Config
import backend.dev.database.DatabaseProvider
import backend.dev.database.DatabaseProviderImpl
import backend.dev.database.injection.DaoInjection
import backend.dev.modules.injection.ModulesInjection
import backend.dev.util.JsonFileManager
import backend.dev.util.JsonFileManagerContract
import com.typesafe.config.ConfigFactory
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

fun main() {
    val api = TrafficApiImpl()

    val environment = System.getenv()["ENVIRONMENT"] ?: handleDefaultEnvironment()
    val config = extractConfig(environment,HoconApplicationConfig(ConfigFactory.load()))



    embeddedServer(Netty, port = config.port){
        module{
            install(Koin){
                modules(
                    module{
                        single { config }
                        single<DatabaseProvider> { DatabaseProviderImpl() }
                        single<JsonFileManagerContract>{JsonFileManager}
                    },
                    ApiInjection.koinBeans,
                    ModulesInjection.koinBeans,
                    DaoInjection.koinBeans
                )
            }
            main()
        }

    }.start(wait = true)
    api.getAllTraffic()
    api.getTrafficByIp("192.198.1.1")
}

fun Application.main() {
    module()
}

fun extractConfig(environment: String, hoconConfig: HoconApplicationConfig): Config{
    val hoconEnvironment = hoconConfig.config("ktor.deployment.$environment")
    return Config(
        hoconEnvironment.property("host").getString(),
        Integer.parseInt(hoconEnvironment.property("port").getString()),
        hoconEnvironment.property("databaseHost").getString(),
        hoconEnvironment.property("databasePort").getString(),

    )
}

fun handleDefaultEnvironment(): String {
    println("Falling back to default environment 'dev'")
    return "dev"
}
