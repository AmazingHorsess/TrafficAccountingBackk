package backend.api.Instrumentation

import backend.dev.model.TrafficLogs
import kotlinx.datetime.Clock

object TrafficLogsTestApi {
    fun fill(): List<TrafficLogs> {
        return listOf(
            TrafficLogs(
                src_ip = "192.168.1.1",
                dst_ip = "127.0.0.2",
                src_port = 8080,
                dst_port = 7070,
                packet_length = 1231231,
                username = "firstUser",
                ts = Clock.System.now()
            ),
            TrafficLogs(
                src_ip = "192.168.1.1",
                dst_ip = "127.0.0.2",
                src_port = 8080,
                dst_port = 7070,
                packet_length = 1231231,
                username = "firstUser",
                ts = Clock.System.now()
            ),
            TrafficLogs(
                src_ip = "192.168.1.2",
                dst_ip = "127.0.0.2",
                src_port = 8080,
                dst_port = 7070,
                packet_length = 1231231,
                username = "firstUser",
                ts = Clock.System.now()
            ),
            TrafficLogs(
                src_ip = "192.168.1.3",
                dst_ip = "127.0.0.2",
                src_port = 8080,
                dst_port = 7070,
                packet_length = 1231231,
                username = "firstUser",
                ts = Clock.System.now()
            ),
            TrafficLogs(
                src_ip = "192.168.1.4",
                dst_ip = "127.0.0.2",
                src_port = 8080,
                dst_port = 7070,
                packet_length = 1231231,
                username = "firstUser",
                ts = Clock.System.now()
            ),
            TrafficLogs(
                src_ip = "192.168.1.5",
                dst_ip = "127.0.0.2",
                src_port = 8080,
                dst_port = 7070,
                packet_length = 1231231,
                username = "firstUser",
                ts = Clock.System.now()
            ),
        )
    }
}