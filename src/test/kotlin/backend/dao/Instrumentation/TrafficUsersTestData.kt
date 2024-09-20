package backend.dao.Instrumentation

import backend.dev.database.dao.tables.TrafficUsers
import org.jetbrains.exposed.sql.insert

internal object TrafficUsersTestData {
    fun fill(){
        TrafficUsers.insert {
            it[src_ip] = "192.168.1.1"
            it[username] = "firstUser"
        }
        TrafficUsers.insert {
            it[src_ip] = "192.168.1.2"
            it[username] = "secondUser"
        }
        TrafficUsers.insert {
            it[src_ip] = "192.168.1.3"
            it[username] = "thirdUser"
        }
        TrafficUsers.insert {
            it[src_ip] = "192.168.1.4"
            it[username] = "fourthUser"
        }
        TrafficUsers.insert {
            it[src_ip] = "192.168.1.5"
            it[username] = "FiveUser"
        }


    }
}