package backend.dev

import backend.dev.api.traffic.injection.ApiInjection
import backend.dev.config.Config
import backend.dev.database.DatabaseProvider
import backend.dev.database.DatabaseProviderImpl
import backend.dev.database.injection.DaoInjection
import backend.dev.modules.injection.ControllersInjection
import backend.dev.modules.traffic.TrafficLogsController
import backend.dev.modules.traffic.TrafficLogsControllerImpl
import backend.dev.util.*
import com.typesafe.config.ConfigFactory
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.datetime.Clock
import org.koin.dsl.module
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main() {

    val environment = System.getenv()["ENVIRONMENT"] ?: handleDefaultEnvironment()
    val config = extractConfig(environment,HoconApplicationConfig(ConfigFactory.load()))
    embeddedServer(Netty, port = config.port){
        println("Starting instance in ${config.host}:${config.port}")

        module{
            install(Koin){
                printLogger(level = org.koin.core.logger.Level.DEBUG)
                slf4jLogger()
                modules(
                    listOf(
                        module {
                            single { config }
                            single<JsonFileManagerContract> { JsonFileManager }
                            single<TrafficLogsController> { TrafficLogsControllerImpl() }
                            single<DatabaseProvider> { DatabaseProviderImpl() }
                            single<DateFilterService> { DateFilterServiceImpl()}
                        },
                        ApiInjection.koinBeans,
                        DaoInjection.koinBeans,
                        ControllersInjection.koinBeans
                    )
                )
            }
            main()
        }

    }.start(wait = true)

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
    return "prod"
}
