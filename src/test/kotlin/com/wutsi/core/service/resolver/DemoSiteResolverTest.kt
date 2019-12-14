package com.wutsi.core.service.resolver

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import javax.servlet.http.HttpServletRequest

@RunWith(MockitoJUnitRunner::class)
class DemoSiteResolverTest {
    @Mock
    lateinit var request: HttpServletRequest

    @InjectMocks
    lateinit var resolver: DemoSiteResolver

    @Test
    fun hostnameHTTP() {
        `when`(request.serverName).thenReturn("test.wutsi.com")
        `when`(request.serverPort).thenReturn(80)

        Assert.assertEquals(resolver.hostname(), "test.wutsi.com")
    }

    @Test
    fun hostnameHTTPS() {
        `when`(request.serverName).thenReturn("test.wutsi.com")
        `when`(request.serverPort).thenReturn(443)

        Assert.assertEquals(resolver.hostname(), "test.wutsi.com")
    }

    @Test
    fun hostnameDifferentPost() {
        `when`(request.serverName).thenReturn("test.wutsi.com")
        `when`(request.serverPort).thenReturn(8881)

        Assert.assertEquals(resolver.hostname(), "test.wutsi.com:8881")
    }}
