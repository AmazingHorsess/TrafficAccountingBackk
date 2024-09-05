package backend.dev.api.traffic.injection

import backend.dev.api.traffic.TrafficApi
import backend.dev.api.traffic.TrafficApiImpl
import org.koin.dsl.module
import org.koin.dsl.single

object ApiInjection {
    val koinBeans = module {
        single<TrafficApi> { TrafficApiImpl() }
    }
}