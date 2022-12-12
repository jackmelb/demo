package com.anz.demo.service;

import com.anz.demo.model.Account;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountService {
    List<Account> getAccountsByUserId(String userId, Pageable pageable);
}
