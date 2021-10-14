package com.agilityio.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.util.matcher.AntPathRequestMatcher


@Configuration
@Order(2)
class UserConfigurationAdapter: WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.antMatcher("/files/**")
            .authorizeRequests().anyRequest().hasRole("USER")
            .and()
            .exceptionHandling()
            .defaultAuthenticationEntryPointFor(
                loginUrlAuthenticationEntryPointWithWarning(),
                AntPathRequestMatcher("/user/login")
            )
            .defaultAuthenticationEntryPointFor(
                loginUrlAuthenticationEntryPoint(),
                AntPathRequestMatcher("/user/**")
            )
    }

    @Bean
    fun loginUrlAuthenticationEntryPoint(): AuthenticationEntryPoint? {
        return LoginUrlAuthenticationEntryPoint("/login")
    }

    @Bean
    fun loginUrlAuthenticationEntryPointWithWarning(): AuthenticationEntryPoint? {
        return LoginUrlAuthenticationEntryPoint("/loginWarning")
    }
}