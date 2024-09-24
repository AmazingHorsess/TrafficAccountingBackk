package backend.dev.parser

import backend.dev.database.dao.tables.TrafficUsers
import backend.dev.model.TrafficLogs
import kotlinx.datetime.*
import kotlinx.datetime.format.DateTimeFormat
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import java.io.File
import java.time.format.DateTimeFormatter
fun parseMonth(month: String): Month {
    return when (month.uppercase()) {
        "JAN" -> Month.JANUARY
        "FEB" -> Month.FEBRUARY
        "MAR" -> Month.MARCH
        "APR" -> Month.APRIL
        "MAY" -> Month.MAY
        "JUN" -> Month.JUNE
        "JUL" -> Month.JULY
        "AUG" -> Month.AUGUST
        "SEP" -> Month.SEPTEMBER
        "OCT" -> Month.OCTOBER
        "NOV" -> Month.NOVEMBER
        "DEC" -> Month.DECEMBER
        else -> throw IllegalArgumentException("Invalid month: $month")
    }
}
fun initDatabase() {
    println("Initializing database...")
    val dbUrl = "jdbc:mysql://localhost:3306/traffic_accounting"
    val dbUser = "root"
    val dbPassword = "1123581321_q"

    try {
        Database.connect(url = dbUrl, driver = "com.mysql.cj.jdbc.Driver", user = dbUser, password = dbPassword)
        println("Connected to database successfully.")

        transaction {
            println("Creating tables if they don't exist...")
            SchemaUtils.create(TrafficUsers, backend.dev.database.dao.tables.TrafficLogs)
            println("Tables created or already exist.")
        }
    } catch (e: Exception) {
        println("Error initializing database: ${e.message}")
    }
}

fun parseLogLine(logLine: String): TrafficLogs? {
    val regex = Regex("""(\w+\s+\d+\s+\d+:\d+:\d+)\s+\S+\s+IPTables-Forward:\s+IN=\S+\s+OUT=\S+\s+SRC=(\d+\.\d+\.\d+\.\d+)\s+DST=(\d+\.\d+\.\d+\.\d+)\s+LEN=(\d+).*?PROTO=TCP\s+SPT=(\d+)\s+DPT=(\d+)""")
    val matchResult = regex.find(logLine)

    if (matchResult != null) {
        val (timestampStr, srcIp, dstIp, packetLength, srcPort, dstPort) = matchResult.destructured
        val parts = timestampStr.split(" ")
        val month = parseMonth(parts[0])
        val day = parts[1].toInt()
        val timeParts = parts[2].split(":")
        val hour = timeParts[0].toInt()
        val minute = timeParts[1].toInt()
        val second = timeParts[2].toInt()
        val localDateTime = LocalDateTime(
            year = 2024, // Укажите нужный год
            month = month,
            dayOfMonth = day,
            hour = hour,
            minute = minute,
            second = second
        )
        val timestamp = localDateTime.toInstant(TimeZone.UTC)

        return TrafficLogs(
            src_ip = srcIp,
            dst_ip = dstIp,
            packet_length = packetLength.toLong(),
            src_port = srcPort.toInt(),
            dst_port = dstPort.toInt(),
            ts = timestamp,
            username = null
        )
    } else {
        println("No match found for line: $logLine")
        return null
    }
}

fun processLogFile(filePath: String) {
    val logsBatch = mutableListOf<TrafficLogs>()
    File(filePath).forEachLine { logLine ->
        println("Processing line: $logLine")
        parseLogLine(logLine)?.let { logsBatch.add(it) }
    }

    if (logsBatch.isNotEmpty()) {
        insertBatchIntoDatabase(logsBatch)
    }
}

fun insertBatchIntoDatabase(logsBatch: List<TrafficLogs>) {
    transaction {
        logsBatch.forEach { log ->
            // Сохранение или получение пользователя
            val userId = TrafficUsers.select { TrafficUsers.src_ip eq log.src_ip }
                .map { it[TrafficUsers.src_ip] }
                .firstOrNull() ?: run {
                TrafficUsers.insert {
                    it[src_ip] = log.src_ip
                    it[username] = null // или задайте имя пользователя, если есть
                }
                log.src_ip
            }

            // Сохранение лога
            backend.dev.database.dao.tables.TrafficLogs.insert {
                it[src_ip] = log.src_ip
                it[dst_ip] = log.dst_ip
                it[src_port] = log.src_port
                it[dst_port] = log.dst_port
                it[packet_length] = log.packet_length
                it[ts] = log.ts
            }
        }
    }
}

private fun main() {
    initDatabase()
    val logFilePath = "C:\\Users\\r.ishmenyov\\IdeaProjects\\TrafficAccounting\\src\\main\\kotlin\\backend\\dev\\parser\\logs\\sys.log"
    processLogFile(logFilePath)
}