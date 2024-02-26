package com.weight.bridge.data.local

import com.weight.bridge.data.local.dao.BridgeTicketDao
import com.weight.bridge.domain.repository.RealmRepository
import com.weight.bridge.util.Constant.SORT_BY_DRIVER_NAME
import com.weight.bridge.util.Constant.SORT_BY_LATEST_NEW
import com.weight.bridge.util.Constant.SORT_BY_LICENSE_NUMBER
import com.weight.bridge.util.Constant.SORT_BY_NEW_LATEST
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.Sort
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

    override fun getAllTicket(sortBy: String): Flow<List<BridgeTicketDao>> {
        var query = realm.query<BridgeTicketDao>()
        query = when (sortBy) {
            SORT_BY_NEW_LATEST -> query.sort("timeEnter", Sort.DESCENDING)
            SORT_BY_LATEST_NEW -> query.sort("timeEnter", Sort.ASCENDING)
            SORT_BY_DRIVER_NAME -> query.sort("driverName", Sort.ASCENDING)
            SORT_BY_LICENSE_NUMBER -> query.sort("truckLicenseNumber", Sort.ASCENDING)
            else -> query
        }
        return query.asFlow().map { result -> result.list }
    }
}