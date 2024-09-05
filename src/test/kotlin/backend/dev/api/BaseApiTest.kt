package backend.dev.api

import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin

abstract class BaseApiTest {

    init {
        stopKoin()
    }

    fun startInjection(module: Module) {
        startKoin {
            modules(
            )
        }
    }
}