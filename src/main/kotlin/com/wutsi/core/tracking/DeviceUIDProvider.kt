package com.wutsi.core.tracking

import java.util.UUID
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

open class DeviceUIDProvider{
    companion object {
        const val COOKIE_NAME = "__w_duaid"
    }

    open fun get(request: HttpServletRequest) : String {
        val cookie = getCookie(request)
        val value = if (cookie != null) cookie.value else request.getAttribute(COOKIE_NAME)?.toString()
        return if (value == null) UUID.randomUUID().toString() else value
    }


    open fun set(duid: String = UUID.randomUUID().toString(), request: HttpServletRequest, response: HttpServletResponse) {
        request.setAttribute(COOKIE_NAME, duid)

        var cookie = getCookie(request)
        if (cookie == null){
            cookie = Cookie(COOKIE_NAME, duid)
            response.addCookie(cookie)
        } else {
            cookie.value = duid
        }
    }

    private fun getCookie(request: HttpServletRequest) = request.cookies?.find { it.name == COOKIE_NAME }
}
