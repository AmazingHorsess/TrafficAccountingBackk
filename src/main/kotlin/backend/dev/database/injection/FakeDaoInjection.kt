package backend.dev.database.injection

import backend.dev.database.dao.FakeTrafficDaoImpl
import backend.dev.database.dao.TrafficLogsDao
import backend.dev.util.Qualifiers
import org.koin.dsl.module

object FakeDaoInjection {
    val koinBeans = module {
        single<TrafficLogsDao>(Qualifiers.devQualifier) { FakeTrafficDaoImpl() }
    }
}