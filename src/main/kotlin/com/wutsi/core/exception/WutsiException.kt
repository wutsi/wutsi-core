package com.wutsi.blog.exception

open class WutsiException(msg:String?, cause: Throwable?=null) : RuntimeException(msg, cause)
