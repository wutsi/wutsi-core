package com.wutsi.core.error

data class ErrorDto (
        val code:String = "",
        val message:String = "",
        val exception:String? = null,
        val stackTrace:List<String>? = null
)
