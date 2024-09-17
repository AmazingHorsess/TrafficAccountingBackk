package backend.dev.modules.injection

import backend.dev.modules.traffic.FakeTrafficController
import backend.dev.modules.traffic.TrafficController
import backend.dev.util.Qualifiers
import org.koin.dsl.module

object FakeControllersInjection {
    val koinBeans = module {
        single<TrafficController>(Qualifiers.devQualifier) { FakeTrafficController() }

    }
}