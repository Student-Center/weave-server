package com.studentcenter.weave.bootstrap.common.config

import com.studentcenter.weave.bootstrap.common.security.resolver.RegisterTokenArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(
    private val registerTokenArgumentResolver: RegisterTokenArgumentResolver
) : WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(registerTokenArgumentResolver)
        super.addArgumentResolvers(resolvers)
    }
}
