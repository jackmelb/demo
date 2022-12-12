package com.anz.demo.mapper.impl;

import com.anz.demo.dto.AccountResponse;
import com.anz.demo.mapper.AccountMapper;
import com.anz.demo.model.Account;
import org.modelmapper.ModelMapper;

public class AccountMapperImpl implements AccountMapper {

    private final ModelMapper modelMapper;

    public AccountMapperImpl(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AccountResponse fromDomain(Account account) {
        return modelMapper.map(account, AccountResponse.class);
    }

    @Override
    public Account toDomain(AccountResponse accountResponse) {
        return modelMapper.map(accountResponse, Account.class);
    }
}
