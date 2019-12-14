package com.wutsi.core.service.resolver

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import javax.servlet.http.HttpServletRequest

@RunWith(MockitoJUnitRunner::class)
class HostStoreResolverTest {
    @Mock
    lateinit var request: HttpServletRequest

    @InjectMocks
    lateinit var resolver: HostStoreResolver

    @Test
    fun get() {
        Mockito.`when`(request.serverName).thenReturn("test.wutsi.com")

        Assert.assertEquals(resolver.name(), "test")
    }

}
