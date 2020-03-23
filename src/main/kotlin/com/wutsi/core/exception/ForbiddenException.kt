package com.wutsi.core.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value= HttpStatus.FORBIDDEN)
class ForbiddenException(msg:String?, ex:Throwable? = null) : WutsiException(msg)
