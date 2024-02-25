package com.weight.bridge.domain.manager

import com.weight.bridge.data.local.dao.BridgeTicketDao
import com.weight.bridge.domain.dto.BridgeTicketDto
import com.weight.bridge.util.toNormalizeDouble
import com.weight.bridge.util.toNormalizeString

fun BridgeTicketDao.toBridgetTicketDto(): BridgeTicketDto {
    return BridgeTicketDto(
        primaryCode,
        timeEnter,
        truckLicenseNumber,
        driverName,
        inboundWeight.toNormalizeString(),
        outboundWeight.toNormalizeString()
    )
}

fun toBridgeTicketDao(dto: BridgeTicketDto) = BridgeTicketDao().apply {
    timeEnter = dto.timeEnter
    truckLicenseNumber = dto.truckLicenseNumber
    driverName = dto.driverName
    inboundWeight = dto.inboundWeight.toNormalizeDouble()
    outboundWeight = dto.outboundWeight.toNormalizeDouble()
}