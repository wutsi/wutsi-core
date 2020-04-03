![](https://github.com/wutsi/wutsi-core/workflows/build/badge.svg)
[![](https://img.shields.io/codecov/c/github/wutsi/wutsi-core/master.svg)](https://codecov.io/gh/wutsi/wutsi-core)
![JDK](https://img.shields.io/badge/jdk-1.8-brightgreen.svg)
![](https://img.shields.io/badge/language-kotlin-blue.svg)

# wutsi-core
This is a library to provide standardized solutions for commons problems encountered by developers who are building webservices.
 
The main benefit of this library is to help developers to reduce boilerplate in their code. 


# Features
- HTTP Utility for calling webservices
- Commons HTTP Exceptions for webservices
- Transactional logger for webservice
- Integration of tracing HTTP headers for correlating HTTP calls between different webservices
- API for managing files on local filesystem or cloud 

# Dependencies
- javax-servlet.servlet-api (scope: provided)
- com.fasterxml.jackson.core.jackson-databind (scope: provided)
- org.springframework.spring-context (scope: provided)
- org.springframework.spring-core (scope: provided)
- org.springframework.spring-web (scope: provided)
- org.slf4j.slf4j-api (scope: provided)
