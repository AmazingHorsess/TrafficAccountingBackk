package backend.dev.modules

import org.koin.core.component.KoinComponent

abstract class BaseController: KoinComponent {

    //:TODO dbprovidercontract
    //private val dbProvider by inject

    suspend fun <T> dbQuery(block: () -> T){
        //return dbProvider.dbQuery(block)
    }
}