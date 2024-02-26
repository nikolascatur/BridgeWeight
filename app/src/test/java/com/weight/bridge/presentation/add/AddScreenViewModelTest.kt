package com.weight.bridge.presentation.add

import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.weight.bridge.BaseUnitTestViewModel
import com.weight.bridge.domain.dto.BridgeTicketDto
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations


class AddScreenViewModelTest : BaseUnitTestViewModel() {

    lateinit var viewModel: AddScreenViewModel

    val dtoExample = BridgeTicketDto(
        primaryCode = "aa",
        timeEnter = 10,
        truckLicenseNumber = "AB1000DD",
        driverName = "Sumanto Makan ciki",
        inboundWeight = "400",
        outboundWeight = "200"
    )

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = AddScreenViewModel(repositoryManager)
    }

    @Test
    fun `get data from repository`() = runBlocking {
        whenever(repositoryManager.getTicket("aa")).thenReturn(
            dtoExample
        )
        with(viewModel) {
            getData("aa")
            verify(repositoryManager, times(1)).getTicket("aa")
            assertEquals(
                viewModel.state.value, AddScreenState(
                    timeEnter = 10,
                    truckLicenseNumber = "AB1000DD",
                    driverName = "Sumanto Makan ciki",
                    inboundWeight = "400",
                    outboundWeight = "200",
                    isGetData = true
                )
            )
        }
    }

    @Test
    fun `save data to ticket success`() = runBlocking {
        viewModel.saveData(dtoExample)
        verify(repositoryManager, times(1)).editTicket(dtoExample)
        assertEquals(true, viewModel.state.value.isSubmitSuccess)
    }

    @Test
    fun `create new ticket success`() = runBlocking {
        viewModel.saveTicket(dtoExample)
        verify(repositoryManager, times(1)).createTicket(dtoExample)
        assertEquals(true, viewModel.state.value.isSubmitSuccess)
    }

}