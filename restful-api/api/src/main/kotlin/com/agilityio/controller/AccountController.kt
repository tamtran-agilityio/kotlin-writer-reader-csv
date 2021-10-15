package com.agilityio.controller

import com.agilityio.model.Account
import com.agilityio.model.Product
import com.agilityio.repository.AccountRepository
import com.agilityio.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/accounts")
class AccountController {
    @Autowired
    lateinit var accountRepository: AccountRepository

    @GetMapping
    fun getAllProducts(): List<Account> = accountRepository.findAll()

}