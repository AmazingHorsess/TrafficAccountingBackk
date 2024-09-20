package backend.dev.api.traffic.injection

import backend.dev.api.traffic.TrafficLogsApi
import backend.dev.api.traffic.TrafficLogsApiImpl
import backend.dev.api.traffic.TrafficUsersApi
import backend.dev.api.traffic.TrafficUsersApiImpl
import org.koin.dsl.module

object ApiInjection {

    val koinBeans = module {

        single<TrafficLogsApi>{TrafficLogsApiImpl()}
        single<TrafficUsersApi>{TrafficUsersApiImpl()}

    }
}