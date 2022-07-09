package com.wutsi.core.tracking

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@ExtendWith(MockitoExtension::class)
class DeviceUIDFilterTest {
    @Mock
    lateinit var request: HttpServletRequest

    @Mock
    lateinit var response: HttpServletResponse

    @Mock
    lateinit var chain: FilterChain

    @Mock
    lateinit var duid: DeviceUIDProvider

    @InjectMocks
    lateinit var filter: DeviceUIDFilter

    @Test
    fun doFilter() {
        doReturn("xxx").whenever(duid).get(request)

        filter.doFilter(request, response, chain)

        verify(duid).set("xxx", request, response)
        verify(chain).doFilter(request, response)
    }

    @Test
    fun doFilterWithError() {
        doReturn("the-id").whenever(duid).get(request)
        `when`(chain.doFilter(request, response)).thenThrow(ServletException::class.java)

        try {
            filter.doFilter(request, response, chain)

            verify(duid).set("the-id", request, response)
            verify(chain).doFilter(request, response)
        } catch (ex: Exception) {

        }
    }
}
