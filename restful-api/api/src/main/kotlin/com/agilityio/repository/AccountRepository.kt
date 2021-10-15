package com.agilityio.repository

import com.agilityio.model.Account
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountRepository: MongoRepository<Account, Long> {
    fun findByEmail(email: String): Optional<Account>
}