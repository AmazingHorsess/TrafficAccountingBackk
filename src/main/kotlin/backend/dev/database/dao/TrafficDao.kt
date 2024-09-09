package backend.dev.database.dao

import backend.dev.model.NetworkTraffic
import backend.dev.model.PutUsernameInIp

interface TrafficDao {
    fun getAllTraffic(): List<NetworkTraffic>
    fun getTrafficByIp(sourceIp: String): List<NetworkTraffic?>
    fun updateUsernameOnIp(putUsername: PutUsernameInIp): NetworkTraffic?
}