package com.wutsi.core.service.resolver

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DemoStoreResolverTest{
    @InjectMocks
    lateinit var resolver: DemoStoreResolver

    @Test
    fun name() {
        assertEquals(resolver.name(), "demo")
    }
}
