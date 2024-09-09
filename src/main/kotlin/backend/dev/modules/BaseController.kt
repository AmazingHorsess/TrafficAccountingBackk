package backend.dev.modules

import backend.dev.database.DatabaseProvider
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BaseController: KoinComponent {


    private val dbProvider by inject<DatabaseProvider>()


    suspend fun <T> dbQuery(block: () -> T): T {
        return dbProvider.dbQuery(block)
    }
}