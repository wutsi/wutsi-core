package com.wutsi.core.storage

import java.net.URL

interface StorageVisitor {
    fun visit(url: URL)
}
