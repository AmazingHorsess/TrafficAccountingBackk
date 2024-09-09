package backend.dev.database.injection

import backend.dev.database.dao.NetworkTraffic
import backend.dev.database.dao.TrafficDao
import org.koin.dsl.module

object DaoInjection {
    val koinBeans = module {
        single<TrafficDao> { NetworkTraffic}
    }
}