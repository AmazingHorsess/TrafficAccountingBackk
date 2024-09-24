package backend.dev.util

import backend.dev.database.dao.tables.TrafficLogs.ts
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.SqlExpressionBuilder.between
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greaterEq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.lessEq
import org.jetbrains.exposed.sql.andWhere
import kotlin.time.Duration

class DateFilterServiceImpl: DateFilterService {

    override fun processDates(startDate: Instant?, endDate: Instant?): Pair<Instant, Instant> {
        val initialStartDate = startDate ?: (Clock.System.now() - Duration.parse("PT5M"))
        val initialEndDate = endDate ?: Clock.System.now()
        val finalStartDate = removeMicroseconds(initialStartDate)
        val finalEndDate = removeMicroseconds(initialEndDate)
        return finalStartDate to finalEndDate
    }

    override fun applySqlDateFilter(query: Query, startDate: Instant?, endDate: Instant?): Query {
        return query.apply {
            when {
                startDate != null && endDate != null -> andWhere { ts.between(startDate, endDate) }
                startDate != null -> andWhere { ts greaterEq startDate }
                endDate != null -> andWhere { ts lessEq endDate }
            }

        }
    }



        private fun removeMicroseconds(instant: Instant): Instant {
            val millisecondsSinceEpoch = instant.toEpochMilliseconds()
            val secondsSinceEpoch = millisecondsSinceEpoch / 1000
            return Instant.fromEpochSeconds(secondsSinceEpoch)
        }
    }

interface DateFilterService {
    fun processDates(startDate: Instant?, endDate: Instant?): Pair<Instant, Instant>
    fun applySqlDateFilter(query: Query, startDate: Instant?, endDate: Instant?): Query
}