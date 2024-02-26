package com.weight.bridge.presentation.list

import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.weight.bridge.BaseUnitTestViewModel
import com.weight.bridge.domain.dto.BridgeTicketDto
import com.weight.bridge.domain.manager.toBridgeTicketDao
import com.weight.bridge.util.Constant
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class ListScreenViewModelTest() : BaseUnitTestViewModel() {

    lateinit var viewModel: ListScreenViewModel

    val dtoExample = BridgeTicketDto(
        primaryCode = "aa",
        timeEnter = 10,
        truckLicenseNumber = "AB1000DD",
        driverName = "Sumanto Makan ciki",
        inboundWeight = "400",
        outboundWeight = "200"
    )

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = ListScreenViewModel(repositoryManager)
    }

    @Test
    fun `do sort action`() = runBlocking {
        val dao = toBridgeTicketDao(dtoExample)
        whenever(repositoryManager.getAllTicket(Constant.SORT_BY_LATEST_NEW)).thenReturn(flow {
            emit(listOf(dao))
        })
        with(viewModel) {
            sortingAction(Constant.SORT_BY_LATEST_NEW)
            assertEquals(Constant.SORT_BY_LATEST_NEW, state.value.sorting)
            verify(repositoryManager, times(1)).getAllTicket(Constant.SORT_BY_LATEST_NEW)
            assertEquals(listOf(dao), state.value.listItem)
        }
    }

    @Test
    fun `delete action`() = runBlocking {
        with(viewModel) {
            val dao = toBridgeTicketDao(dtoExample)
            deleteTicket(dao)
            verify(repositoryManager).deleteTicket(dao)
        }
    }

}