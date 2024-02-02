package com.studentcenter.weave.bootstrap.controller

import com.studentcenter.weave.application.user.vo.UserAuthentication
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class JwtAuthenticationFilterTestController {

    @GetMapping("/jwt-authentication-filter-test")
    fun jwtAuthenticationFilterTest(): ResponseEntity<Unit> {
        val userAuthentication: UserAuthentication? = SecurityContextHolder
            .getContext<UserAuthentication>()
            ?.getAuthentication()

        if (userAuthentication != null) {
            return ResponseEntity.ok().build()
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
    }

}
