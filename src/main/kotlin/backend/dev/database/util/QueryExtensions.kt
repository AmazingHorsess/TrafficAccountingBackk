package backend.dev.database.util

import backend.dev.database.dao.tables.TrafficLogs.ts
import kotlinx.datetime.Instant
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.andWhere

internal fun applyDateFilter(query: Query, startDate: Instant?, endDate: Instant?): Query {
    return query.apply {
        when {
            startDate != null && endDate != null -> andWhere { ts.between(startDate, endDate) }
            startDate != null -> andWhere { ts greaterEq startDate }
            endDate != null -> andWhere { ts lessEq endDate }
        }
    }
}