package com.wutsi.core.service.resolver

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
@Profile("!local")
class HostSiteResolver(private val request: HttpServletRequest): SiteResolver {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(HostSiteResolver::class.java)
    }

    override fun hostname(): String {
        val serverName = request.serverName
        val index = serverName.indexOf('.')
        val hostname = serverName.substring(index+1)

        logger.info("URL=${request.requestURL} Host=$hostname")
        return hostname;
    }
}
