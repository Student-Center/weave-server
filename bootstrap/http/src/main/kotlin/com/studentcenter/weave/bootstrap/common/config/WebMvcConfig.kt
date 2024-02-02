package com.studentcenter.weave.bootstrap.common.config

import com.studentcenter.weave.bootstrap.common.security.interceptor.AuthorizationInterceptor
import com.studentcenter.weave.bootstrap.common.security.resolver.RegisterTokenArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(
    private val registerTokenArgumentResolver: RegisterTokenArgumentResolver,
    private val authorizationInterceptor: AuthorizationInterceptor
) : WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(registerTokenArgumentResolver)
        super.addArgumentResolvers(resolvers)
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authorizationInterceptor)
        super.addInterceptors(registry)
    }

}
