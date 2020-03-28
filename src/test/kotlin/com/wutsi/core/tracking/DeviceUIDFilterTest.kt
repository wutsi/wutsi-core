package com.wutsi.core.tracking

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RunWith(MockitoJUnitRunner::class)
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
        `when`(duid.get(request)).thenReturn("xxx")

        filter.doFilter(request, response, chain)

        verify(duid).set("xxx", request, response)
        verify(chain).doFilter(request, response)
    }

    @Test
    fun doFilterWithError() {
        `when`(duid.get(request)).thenReturn("the-id")
        `when`(chain.doFilter(request, response)).thenThrow(ServletException::class.java)

        try {
            filter.doFilter(request, response, chain)

            verify(duid).set("the-id", request, response)
            verify(chain).doFilter(request, response)
        } catch (ex: Exception){

        }
    }
}
