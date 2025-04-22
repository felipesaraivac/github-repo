package com.saraiva.github

import com.saraiva.github.core.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.Dispatchers
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class CoroutinesRule(
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    val scope = TestScope(dispatcher)

    val dispatcherProvider by lazy {
        object : DispatcherProvider {
            override fun default(): CoroutineDispatcher = dispatcher
            override fun io(): CoroutineDispatcher = dispatcher
            override fun main(): CoroutineDispatcher = dispatcher
            override fun unconfined(): CoroutineDispatcher = dispatcher
        }
    }

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}