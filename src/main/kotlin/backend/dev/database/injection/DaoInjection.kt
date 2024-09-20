package backend.dev.database.injection

import backend.dev.database.dao.TrafficLogsDao
import backend.dev.database.dao.TrafficUsersDao
import backend.dev.database.dao.tables.TrafficLogs
import backend.dev.database.dao.tables.TrafficUsers
import org.koin.dsl.module

object DaoInjection {
    val koinBeans = module {
        single<TrafficLogsDao> { TrafficLogs }
        single<TrafficUsersDao> { TrafficUsers }
    }
}