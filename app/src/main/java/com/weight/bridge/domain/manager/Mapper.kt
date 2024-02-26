package com.weight.bridge.domain.manager

import com.weight.bridge.data.local.dao.BridgeTicketDao
import com.weight.bridge.data.remote.model.BridgeTicket
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

fun BridgeTicketDto.toBridgeTicket(primaryCode: String) = BridgeTicket(
    timeEnter = timeEnter,
    truckLicenseNumber = truckLicenseNumber,
    driverName = driverName,
    inboundWeight = inboundWeight.toNormalizeDouble(),
    outboundWeight = outboundWeight.toNormalizeDouble(),
    primaryCode = primaryCode
)

fun BridgeTicket.toBridgeTicketDto() = BridgeTicketDto(
    timeEnter = timeEnter,
    truckLicenseNumber = truckLicenseNumber,
    driverName = driverName,
    inboundWeight = inboundWeight.toNormalizeString(),
    outboundWeight = outboundWeight.toNormalizeString()
)