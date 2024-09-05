package backend.dev.plugins

import backend.dev.api.traffic.injection.ApiInjection
import backend.dev.util.JsonFileManager
import backend.dev.util.JsonFileManagerContract
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureFrameworks() {
    install(Koin) {
        slf4jLogger()
        modules(module {
            single<HelloService> {
                HelloService {
                    println(environment.log.info("Hello, World!"))
                }
            }
            ApiInjection.koinBeans
            single<JsonFileManagerContract> { JsonFileManager }

        })
    }
}
