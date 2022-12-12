package com.anz.demo.mapper;

import com.anz.demo.dto.AccountResponse;
import com.anz.demo.model.Account;

public interface AccountMapper {

   AccountResponse fromDomain(Account account);
   Account toDomain(AccountResponse account);
}
