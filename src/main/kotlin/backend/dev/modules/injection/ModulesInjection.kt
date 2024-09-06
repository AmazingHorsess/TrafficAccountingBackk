package backend.dev.modules.injection

import backend.dev.api.traffic.TrafficApi
import backend.dev.api.traffic.TrafficApiImpl
import org.koin.dsl.module

object ModulesInjection {
    val koinBeans = module {
        single<TrafficApi> { TrafficApiImpl() }
    }
}