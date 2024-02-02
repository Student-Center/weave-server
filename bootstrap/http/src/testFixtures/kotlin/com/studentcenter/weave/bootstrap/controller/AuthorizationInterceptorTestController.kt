package com.studentcenter.weave.bootstrap.controller

import com.studentcenter.weave.bootstrap.common.security.annotation.Secured
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthorizationInterceptorTestController {

    @Secured
    @GetMapping("/secured-method")
    @ResponseStatus(HttpStatus.OK)
    fun securedMethod() {
        /* secured method */
    }

    @GetMapping("/unsecured-method")
    @ResponseStatus(HttpStatus.OK)
    fun unsecuredMethod() {
        /* unsecured method */
    }

}
