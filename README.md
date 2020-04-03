![](https://github.com/wutsi/wutsi-core/workflows/build/badge.svg)
[![](https://img.shields.io/codecov/c/github/wutsi/wutsi-core/master.svg)](https://codecov.io/gh/wutsi/wutsi-core)
![JDK](https://img.shields.io/badge/jdk-1.8-brightgreen.svg)
![](https://img.shields.io/badge/language-kotlin-blue.svg)


# About
This is a library that provide standardize solutions for solving common problems.


# Motivation
Here are the reasons to use this library:
- **Standardization**: It provide standardizes solution for commons problems encountered by developers.
- **Simplify**: It reduce boilerplate code, no need to re-implement again and again the same solutions in inconsistent way.


# Features
Here are the problems this library solve:
- How to call HTTP webservice?
- How to handle webservice exceptions?
- How to log webservice calls?
- How to correlate logs between different webservices?
- How to manage file on virtual filesystem (local or cloud)? 


# Usage
```xml
<dependency>
    <groupId>com.wutsi</groupId>
    <artifactId>wutsi-core</artifactId>
    <version>[LATEST VERSION]</version>
</dependency>
```

View package details [here](https://github.com/wutsi/wutsi-core/packages)

# Dependencies
- javax-servlet.servlet-api (scope: provided)
- com.fasterxml.jackson.core.jackson-databind (scope: provided)
- org.springframework.spring-context (scope: provided)
- org.springframework.spring-core (scope: provided)
- org.springframework.spring-web (scope: provided)
- org.slf4j.slf4j-api (scope: provided)
