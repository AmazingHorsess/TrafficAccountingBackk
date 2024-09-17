package backend.dev.api.traffic.injection

import backend.dev.api.traffic.FakeTrafficApiImpl
import backend.dev.api.traffic.TrafficLogApi
import backend.dev.util.Qualifiers
import org.koin.dsl.module

object FakeApiInjection {

    val koinBeans = module {
        single<TrafficLogApi>(Qualifiers.devQualifier){ FakeTrafficApiImpl() }
    }
}