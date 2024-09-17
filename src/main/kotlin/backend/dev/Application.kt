package backend.dev

import backend.dev.api.traffic.injection.FakeApiInjection
import backend.dev.config.Config
import backend.dev.database.DatabaseProvider
import backend.dev.database.DatabaseProviderImpl
import backend.dev.database.injection.FakeDaoInjection
import backend.dev.modules.injection.FakeControllersInjection
import backend.dev.modules.traffic.FakeTrafficController
import backend.dev.modules.traffic.TrafficController
import backend.dev.util.JsonFileManager
import backend.dev.util.JsonFileManagerContract
import com.typesafe.config.ConfigFactory
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.core.logger.Logger
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import org.slf4j.event.Level

fun main() {

    val environment = System.getenv()["ENVIRONMENT"] ?: handleDefaultEnvironment()
    val config = extractConfig(environment,HoconApplicationConfig(ConfigFactory.load()))



    embeddedServer(Netty, port = config.port){
        module{
            install(Koin){
                printLogger(level = org.koin.core.logger.Level.DEBUG)
                slf4jLogger()
                modules(
                    module{
                        single { config }
                        single<JsonFileManagerContract>{ JsonFileManager}
                        single<TrafficController> { FakeTrafficController()}
                        single<DatabaseProvider> { DatabaseProviderImpl() }
                        FakeApiInjection.koinBeans
                        FakeDaoInjection.koinBeans
                        FakeControllersInjection.koinBeans

                    }

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
    return "dev"
}
