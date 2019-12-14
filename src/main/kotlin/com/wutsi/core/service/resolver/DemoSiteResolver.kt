package com.wutsi.core.service.resolver

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
@Profile("local")
class DemoSiteResolver(private val request: HttpServletRequest): SiteResolver {
    companion object {
        val logger = LoggerFactory.getLogger(DemoSiteResolver::class.java)
    }

    override fun hostname(): String {
        var hostname = request.serverName

        var port = ""
        val serverPort = request.serverPort
        if (!SiteResolver.standardPorts.contains(serverPort)){
            port = ":$serverPort"
        }

        logger.info("URL=${request.requestURL} Host=$hostname")
        return "$hostname$port"
    }
}
