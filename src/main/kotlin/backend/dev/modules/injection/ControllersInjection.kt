package backend.dev.modules.injection

import backend.dev.modules.traffic.TrafficLogsController
import backend.dev.modules.traffic.TrafficLogsControllerImpl
import backend.dev.modules.traffic.TrafficUsersController
import backend.dev.modules.traffic.TrafficUsersControllerImpl
import org.koin.dsl.module

object ControllersInjection {

    val koinBeans = module {
        single<TrafficLogsController>{ TrafficLogsControllerImpl() }
        single<TrafficUsersController> { TrafficUsersControllerImpl() }
    }
}