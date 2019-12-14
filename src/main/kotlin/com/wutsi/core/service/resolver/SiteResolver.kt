package com.wutsi.core.service.resolver

interface SiteResolver {
    companion object {
        val standardPorts = arrayListOf(80, 443)
    }

    fun hostname() : String
}
