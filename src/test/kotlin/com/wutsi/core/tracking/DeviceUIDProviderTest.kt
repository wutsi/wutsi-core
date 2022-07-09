package com.wutsi.core.tracking

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class DeviceUIDProviderTest {
    @Mock
    lateinit var request: HttpServletRequest

    @Mock
    lateinit var response: HttpServletResponse

    @InjectMocks
    lateinit var provider: DeviceUIDProvider

    @Test
    fun getNullCookieNoAttributeShouldReturnNewUUID() {
        doReturn(null).whenever(request).cookies
        doReturn(null).whenever(request).getAttribute(DeviceUIDProvider.COOKIE_NAME)

        val value = provider.get(request)

        assertEquals(36, value.length)
    }

    @Test
    fun getCookieNotFoundNoAttributeShouldReturnNewUUID() {
        `when`(request.cookies).thenReturn(
            arrayOf(
                Cookie("foo1", "bar1"),
                Cookie("foo2", "bar2"),
                Cookie("foo3", "bar3")
            )
        )
        doReturn(null).whenever(request).getAttribute(DeviceUIDProvider.COOKIE_NAME)

        val value = provider.get(request)

        assertEquals(36, value.length)
    }

    @Test
    fun getNullCookieWithAttributeShouldReturnAttributeValue() {
        doReturn(null).whenever(request).cookies
        doReturn("foo").whenever(request).getAttribute(DeviceUIDProvider.COOKIE_NAME)

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
