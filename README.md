`wutsi-core` offers standard utilities for functionalities commonly used for building software.


![](https://github.com/wutsi/wutsi-core/workflows/master/badge.svg)
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

Package available [here](https://github.com/wutsi/wutsi-core/packages)

# Features

## REST Exceptions
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

All HTTP errors will be mapped to the [REST exceptions](#wutsi-rest-exceptions).

## Logging
- [KVLogger](https://github.com/wutsi/wutsi-core/blob/master/src/main/kotlin/com/wutsi/core/logging/KVLogger.kt)
should be used for outputing logs in the format key/value pair.
- [KVLoggerFilter](https://github.com/wutsi/wutsi-core/blob/master/src/main/kotlin/com/wutsi/core/logging/KVLoggerFilter.kt)
is a Servlet filter that logs each HTTP calls. Each logs will always contains the following information:
  - `HttpRequestURI`: The request URI (Ex. `HttpRequestURI=/v1/user`)
  - `HttpRequestMethod`: The request URI (Ex. `HttpRequestMethod=GET`)
  - `HttpRequestEncoding`: The value of the request header `Accept-Encoding`
  - `HttpRequestType`: The value of the request header `Content-Type`
  - `HttpRequestAuthorization`: The value of the request header `Authorization`
  - `X-Client-ID`: The value of the request header `X-Client-ID`
  - `X-Device-UID`: The value of the request header `X-Device-UID`
  - `User-Agent`: The value of the request header `User-Agent`
  - `X-Trace-ID`: The value of the request header `X-Trace-ID`
  - `X-Message-ID`: The value of the request header `X-Message-ID`
  - `X-Parent-Message-ID`: The value of the request header `X-Parent-Message-ID`
  - `HttpResponseStatus`: The response status code
  - `HttpResponseEncoding`: The response encoding
  - `HttpResponseType`: The response mime type
  - `HttpResponseLength`: The response content length

## Tracking
- [DeviceUIDProvider](https://github.com/wutsi/wutsi-core/blob/master/src/main/kotlin/com/wutsi/core/tracking/DeviceUIDProvider.kt)
is used to get or set the client device unique identifier.
- [DeviceUIDFilter](https://github.com/wutsi/wutsi-core/blob/master/src/main/kotlin/com/wutsi/core/tracking/DeviceUIDFilter.kt)
is a servlet filter that ensure that each HTTP request is stamped with a unique device identifier.


## Storage
- [StorageService](https://github.com/wutsi/wutsi-core/blob/master/src/main/kotlin/com/wutsi/core/storage/StorageService.kt)
is an interface to storing/retrieving files.
- [LocalStorageService](https://github.com/wutsi/wutsi-core/blob/master/src/main/kotlin/com/wutsi/core/storage/StorageService.kt)
is the implementation for storing/retrieving files on a local hard drive.


## Caching
- [CacheService](https://github.com/wutsi/wutsi-core/blob/master/src/main/kotlin/com/wutsi/core/cache/CacheService.kt)
is an interface for accessing a cache.
- [HashMapCacheService](https://github.com/wutsi/wutsi-core/blob/master/src/main/kotlin/com/wutsi/core/cache/HashMapCacheService.kt)
is the implementation with an in-memory hashmap as cache.
- [NullCacheService](https://github.com/wutsi/wutsi-core/blob/master/src/main/kotlin/com/wutsi/core/cache/NullCacheService.kt)
is the implementation to perform no-caching.


## Text Translation
- [TranslateService](https://github.com/wutsi/wutsi-core/blob/master/src/main/kotlin/com/wutsi/core/translate/TranslateService.kt)
is an interface to translating text.
- [NullTranslateService](https://github.com/wutsi/wutsi-core/blob/master/src/main/kotlin/com/wutsi/core/translate/NullTranslateService.kt)
is the implementation that performs no translation, returns the text as is.


## Other
- [DateUtils](https://github.com/wutsi/wutsi-core/blob/master/src/main/kotlin/com/wutsi/core/util/DateUtils.kt)
- [DurationUtils](https://github.com/wutsi/wutsi-core/blob/master/src/main/kotlin/com/wutsi/core/util/DurationUtils.kt)
- [NumberUtils](https://github.com/wutsi/wutsi-core/blob/master/src/main/kotlin/com/wutsi/core/util/NumberUtils.kt)
- [Predicates](https://github.com/wutsi/wutsi-core/blob/master/src/main/kotlin/com/wutsi/core/util/Predicates.kt)
- [CamelCaseToSnakeCaseNamingStrategy](https://github.com/wutsi/wutsi-core/blob/master/src/main/kotlin/com/wutsi/core/hibernate/CamelCaseToSnakeCaseNamingStrategy.kt):
 [Hibernate](https://hibernate.org/) class for supporting mapping from camel case (in POJO) to snake case (in DB).
