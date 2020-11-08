`wutsi-core` core library


![](https://github.com/wutsi/wutsi-core/workflows/build/badge.svg)
![](https://img.shields.io/badge/jdk-1.8-brightgreen.svg)
![](https://img.shields.io/badge/language-kotlin-blue.svg)
![](https://img.shields.io/badge/maven-3.6+-blue)



# Prerequisites
- JDK 1.8
- Maven 3.6+ 


# Installation
```xml
<dependency>
    <groupId>com.wutsi</groupId>
    <artifactId>wutsi-core</artifactId>
    <version>[LATEST VERSION]</version>
</dependency>
```

View package details [here](https://github.com/wutsi/wutsi-core/packages)

# Features
`wutsi-core` offers standard utilities for functionalities commonly used for building software.

## Wutsi REST Exceptions
Wutsi common exception for HTTP REST calls:
- [BadRequestException](https://github.com/wutsi/wutsi-core/blob/master/src/main/kotlin/com/wutsi/core/exception/BadRequestException.kt) for HTTP 400 errors
- [UnauthorizedException](https://github.com/wutsi/wutsi-core/blob/master/src/main/kotlin/com/wutsi/core/exception/UnauthorizedException.kt) for HTTP 401 errors
- [ForbiddenException](https://github.com/wutsi/wutsi-core/blob/master/src/main/kotlin/com/wutsi/core/exception/ForbiddenException.kt) for HTTP 403 errors
- [NotFoundException](https://github.com/wutsi/wutsi-core/blob/master/src/main/kotlin/com/wutsi/core/exception/NotFoundException.kt) for HTTP 404 errors
- [ConflictException](https://github.com/wutsi/wutsi-core/blob/master/src/main/kotlin/com/wutsi/core/exception/ConflictException.kt) for HTTP 409 errors
- [InternalErrorException](https://github.com/wutsi/wutsi-core/blob/master/src/main/kotlin/com/wutsi/core/exception/InternalErrorException.kt) for HTTP 500 errors
 

## HTTP
[Http](https://github.com/wutsi/wutsi-core/blob/master/src/main/kotlin/com/wutsi/core/http/Http.kt)
offers a simple interface for calling REST endpoint via `GET`, `POST`, `PUT`, `DELETE` methods.

All REST calls will include the following headers: 
- `X-Client-ID`: Identifier of the caller
- `X-Device-UID`: Unique Identifier of device of the caller.
- `User-Agent`: User Agent of the client's device.
- `X-Trace-ID`: Identifier representing *Trace*.
- `X-Message-ID`: Identifier representing each *Message* of a *Trace*.
- `X-Parent-Message-ID`: Identifier representing the parent of *Message* (Optional).

All HTTP errors will be mapped to the Wutsi REST exceptions.

