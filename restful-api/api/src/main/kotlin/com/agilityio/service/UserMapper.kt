package com.agilityio.service

import com.agilityio.model.Account
import org.springframework.security.core.userdetails.UserDetails
import java.nio.file.attribute.UserPrincipal

class UserMapper {
    fun userToPrincipal(user: Account): UserDetails? {
        return null
    }
}