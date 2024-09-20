package backend.dao.Instrumentation

import backend.dev.database.dao.tables.TrafficLogs
import backend.dev.database.dao.tables.TrafficLogs.src_port
import backend.dev.database.dao.tables.TrafficLogs.ts
import kotlinx.datetime.Clock
import org.jetbrains.exposed.sql.insert

internal object TrafficLogsTestData {
    fun fill(){
        TrafficLogs.insert {
            it[src_ip] = "192.168.1.1"
            it[dst_ip] = "192.168.0.2"
            it[src_port] = 12345
            it[dst_port] = 54321
            it[packet_length] = 1024L
            it[ts] = Clock.System.now()
        }
        TrafficLogs.insert {
            it[src_ip] = "192.168.1.1"
            it[dst_ip] = "192.168.0.2"
            it[src_port] = 12345
            it[dst_port] = 54321
            it[packet_length] = 1024L
            it[ts] = Clock.System.now()
        }
        TrafficLogs.insert {
            it[src_ip] = "192.168.1.1"
            it[dst_ip] = "192.168.0.2"
            it[src_port] = 12345
            it[dst_port] = 54321
            it[packet_length] = 1024L
            it[ts] = Clock.System.now()
        }
        TrafficLogs.insert {
            it[src_ip] = "192.168.1.2"
            it[dst_ip] = "192.168.0.2"
            it[src_port] = 12345
            it[dst_port] = 54321
            it[packet_length] = 1024L
            it[ts] = Clock.System.now()
        }

        TrafficLogs.insert {
            it[src_ip] = "192.168.1.2"
            it[dst_ip] = "192.168.0.2"
            it[src_port] = 12345
            it[dst_port] = 54321
            it[packet_length] = 1024L
            it[ts] = Clock.System.now()
        }

        TrafficLogs.insert {
            it[src_ip] = "192.168.1.2"
            it[dst_ip] = "192.168.0.2"
            it[src_port] = 12345
            it[dst_port] = 54321
            it[packet_length] = 1024L
            it[ts] = Clock.System.now()
        }

        TrafficLogs.insert {
            it[src_ip] = "192.168.1.3"
            it[dst_ip] = "192.168.0.2"
            it[src_port] = 12345
            it[dst_port] = 54321
            it[packet_length] = 1024L
            it[ts] = Clock.System.now()
        }

        TrafficLogs.insert {
            it[src_ip] = "192.168.1.3"
            it[dst_ip] = "192.168.0.2"
            it[src_port] = 12345
            it[dst_port] = 54321
            it[packet_length] = 1024L
            it[ts] = Clock.System.now()
        }

        TrafficLogs.insert {
            it[src_ip] = "192.168.1.3"
            it[dst_ip] = "192.168.0.2"
            it[src_port] = 12345
            it[dst_port] = 54321
            it[packet_length] = 1024L
            it[ts] = Clock.System.now()
        }
        TrafficLogs.insert {
            it[src_ip] = "192.168.1.4"
            it[dst_ip] = "192.168.0.2"
            it[src_port] = 12345
            it[dst_port] = 54321
            it[packet_length] = 1024L
            it[ts] = Clock.System.now()
        }

        TrafficLogs.insert {
            it[src_ip] = "192.168.1.4"
            it[dst_ip] = "192.168.0.2"
            it[src_port] = 12345
            it[dst_port] = 54321
            it[packet_length] = 1024L
            it[ts] = Clock.System.now()
        }

        TrafficLogs.insert {
            it[src_ip] = "192.168.1.4"
            it[dst_ip] = "192.168.0.2"
            it[src_port] = 12345
            it[dst_port] = 54321
            it[packet_length] = 1024L
            it[ts] = Clock.System.now()
        }

    }
}