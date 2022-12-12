package com.anz.demo.service.impl;

import com.anz.demo.model.Account;
import com.anz.demo.repository.AccountRepository;
import com.anz.demo.service.AccountService;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> getAccountsByUserId(String userId, Pageable pageable) {
        return accountRepository.findAllByUserId(userId, pageable);
    }
}
