package backend.dev.util

import org.koin.core.qualifier.named

object Qualifiers {
    val devQualifier = named("dev")
    val prodQualifier = named("prod")
}