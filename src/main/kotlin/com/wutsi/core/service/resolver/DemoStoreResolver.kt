package com.wutsi.core.service.resolver

import com.wutsi.web.service.resolver.StoreResolver
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile("local")
class DemoStoreResolver: StoreResolver {
    override fun name(): String {
        return "demo"
    }
}
