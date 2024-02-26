package com.weight.bridge.presentation.splash

import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.weight.bridge.BaseUnitTestViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class SplashViewModelTest: BaseUnitTestViewModel() {


    lateinit var viewModel: SplashViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = SplashViewModel(repositoryManager)
    }

    @Test
    fun `do sync action`() = runBlocking {
        viewModel.syncFromServer()
        assertEquals(true, viewModel.isFinish.value)
    }
}