package com.wutsi.blog.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value= HttpStatus.UNAUTHORIZED)
class UnauthorizedException(msg:String) : WutsiException(msg)
