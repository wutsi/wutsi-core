package com.wutsi.core.service.resolver

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
@Profile("!local")
class HostStoreResolver(val request: HttpServletRequest): StoreResolver {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(HostStoreResolver::class.java)
    }

    override fun name(): String {
        val host = request.serverName
        val i = host.indexOf('.')
        if (i > 0){
            val store = host.substring(0, i)
            logger.info("URL=${request.requestURL} Host=$host,  Store=$store")
            return store
        }

        throw IllegalStateException("Unable to extract store from ${request.serverName}")
    }
}
