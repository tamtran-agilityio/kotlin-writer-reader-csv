package com.agilityio.config

import com.agilityio.authentication.RoleName
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint

@Configuration
@Order(1)
class AdminConfigurationAdapter: WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.antMatcher("/v1.0/api/users")
            .authorizeRequests().anyRequest().hasRole(RoleName.ADMIN.toString())
            .and().httpBasic().authenticationEntryPoint(authenticationEntryPoint())
    }

    @Bean
    fun authenticationEntryPoint(): AuthenticationEntryPoint? {
        val entryPoint = BasicAuthenticationEntryPoint()
        entryPoint.realmName = "admin"
        return entryPoint
    }
}