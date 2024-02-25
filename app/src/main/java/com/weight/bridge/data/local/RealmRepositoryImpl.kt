package com.weight.bridge.data.local

import com.weight.bridge.data.local.dao.BridgeTicketDao
import com.weight.bridge.domain.repository.RealmRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RealmRepositoryImpl(private val realm: Realm) : RealmRepository {

    override suspend fun createTicket(bridgeTicketDao: BridgeTicketDao) {
        realm.write {
            copyToRealm(bridgeTicketDao, updatePolicy = UpdatePolicy.ALL)
        }
    }

    override suspend fun editTicket(primaryKey: String, bridgeTicketDao: BridgeTicketDao) {
        realm.write {
            query<BridgeTicketDao>("primaryCode == $0", primaryKey).find().first()?.let {
                it.timeEnter = bridgeTicketDao.timeEnter
                it.truckLicenseNumber = bridgeTicketDao.truckLicenseNumber
                it.driverName = bridgeTicketDao.driverName
                it.inboundWeight = bridgeTicketDao.inboundWeight
                it.outboundWeight = bridgeTicketDao.outboundWeight
            }
        }
    }

    override suspend fun getTicket(id: String): BridgeTicketDao? {
        val list = realm.query<BridgeTicketDao>("primaryCode == $0", id).find()
        return if (list.isNotEmpty()) list.first() else null
    }

    override suspend fun deleteTicket(dao: BridgeTicketDao) {
        realm.write {
            findLatest(dao)?.also { delete(it) }
        }
    }

    override fun getAllTicket(): Flow<List<BridgeTicketDao>> {
        return realm.query<BridgeTicketDao>().asFlow().map { result -> result.list }
    }
}