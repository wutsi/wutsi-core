package com.wutsi.core.exception

open class WutsiException(msg:String?, cause: Throwable?=null) : RuntimeException(msg, cause)
