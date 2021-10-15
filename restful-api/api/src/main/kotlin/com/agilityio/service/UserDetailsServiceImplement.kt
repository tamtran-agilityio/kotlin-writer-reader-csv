package com.agilityio.service

import com.agilityio.model.Account
import com.agilityio.repository.AccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class UserDetailsServiceImplement: UserDetailsService {
    @Autowired
    lateinit var accountRepository: AccountRepository

    override fun loadUserByUsername(email: String): UserDetails? {
        val user = accountRepository
            .findByEmail(email)
        return null
    }
}
