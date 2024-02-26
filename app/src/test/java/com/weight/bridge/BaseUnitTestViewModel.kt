package com.weight.bridge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.weight.bridge.domain.manager.RepositoryManager
import org.junit.Rule
import org.mockito.Mock

abstract class BaseUnitTestViewModel {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    @Mock
    lateinit var repositoryManager: RepositoryManager

}