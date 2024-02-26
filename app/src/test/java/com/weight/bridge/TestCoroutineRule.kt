package com.weight.bridge

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class TestCoroutineRule : TestRule {

    @ExperimentalCoroutinesApi
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    @ExperimentalCoroutinesApi
    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun apply(base: Statement, description: Description?) = object : Statement() {
        override fun evaluate() {
            Dispatchers.setMain(testCoroutineDispatcher)

            base.evaluate()

            Dispatchers.resetMain()
            testCoroutineScope.cleanupTestCoroutines()
        }
    }

    @ExperimentalCoroutinesApi
    fun runBlocking(block: suspend TestCoroutineScope.() -> Unit) =
        testCoroutineScope.runBlockingTest { block() }

}