package com.wutsi.blog.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
class BadRequestException(msg:String, ex:Throwable? = null) : WutsiException(msg, ex)
