package backend.dev.util

import kotlinx.datetime.*


fun Instant.truncateToSeconds(): Instant {
    return this.toLocalDateTime(TimeZone.UTC)
        .run { LocalDateTime(year, monthNumber, dayOfMonth, hour, minute, second) }
        .toInstant(TimeZone.UTC)
}

fun removeMicroseconds(instant: Instant): Instant {
    // Получаем количество миллисекунд от начала эпохи
    val millisecondsSinceEpoch = instant.toEpochMilliseconds()
    // Округляем до целых секунд
    val secondsSinceEpoch = millisecondsSinceEpoch / 1000
    // Создаем новый Instant, округленный до целых секунд
    return Instant.fromEpochSeconds(secondsSinceEpoch)
}