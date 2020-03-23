package com.wutsi.core.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value= HttpStatus.NOT_FOUND)
class NotFoundException(msg:String, ex:Throwable? = null) : WutsiException(msg, ex)
