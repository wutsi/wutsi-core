package com.wutsi.blog.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value= HttpStatus.CONFLICT)
class ConflictException(msg:String, cause: Throwable?=null) : WutsiException(msg, cause)
