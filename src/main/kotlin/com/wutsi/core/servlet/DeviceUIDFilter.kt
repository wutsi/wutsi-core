package com.wutsi.core.servlet

import com.wutsi.core.service.DeviceUIDProvider
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

open class DeviceUIDFilter(private val duid: DeviceUIDProvider): Filter {
    override fun destroy() {
    }

    override fun init(config: FilterConfig?) {
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        try {

            val value = duid.get(request as HttpServletRequest)
            duid.set(value, request, response as HttpServletResponse)

        } finally {
            chain.doFilter(request, response)
        }
    }
}
