package com.wutsi.core.service

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RunWith(MockitoJUnitRunner::class)
class DeviceUIDProviderTest {
    @Mock
    lateinit var request: HttpServletRequest

    @Mock
    lateinit var response: HttpServletResponse

    @InjectMocks
    lateinit var provider: DeviceUIDProvider

    @Test
    fun getNullCookieNoAttributeShouldReturnNewUUID() {
        `when`(request.cookies).thenReturn(null)
        `when`(request.getAttribute(DeviceUIDProvider.COOKIE_NAME)).thenReturn(null)

        val value = provider.get(request)

        assertEquals(36, value.length)
    }

    @Test
    fun getCookieNotFoundNoAttributeShouldReturnNewUUID() {
        `when`(request.cookies).thenReturn(arrayOf(
                Cookie("foo1", "bar1"),
                Cookie("foo2", "bar2"),
                Cookie("foo3", "bar3")
        ))
        `when`(request.getAttribute(DeviceUIDProvider.COOKIE_NAME)).thenReturn(null)

        val value = provider.get(request)

        assertEquals(36, value.length)
    }

    @Test
    fun getNullCookieWithAttributeShouldReturnAttributeValue() {
        `when`(request.cookies).thenReturn(null)
        `when`(request.getAttribute(DeviceUIDProvider.COOKIE_NAME)).thenReturn("foo")

        val value = provider.get(request)

        assertEquals("foo", value)
    }

    @Test
    fun set() {
        provider.set("xxx", request, response)

        verify(request).setAttribute(DeviceUIDProvider.COOKIE_NAME, "xxx")

        val cookie: ArgumentCaptor<Cookie> = ArgumentCaptor.forClass(Cookie::class.java)
        verify(response).addCookie(cookie.capture())
        assertEquals(DeviceUIDProvider.COOKIE_NAME, cookie.value.name)
        assertEquals("xxx", cookie.value.value)
    }
}
