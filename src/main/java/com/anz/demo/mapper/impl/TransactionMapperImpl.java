package com.anz.demo.mapper.impl;

import com.anz.demo.dto.TransactionResponse;
import com.anz.demo.mapper.TransactionMapper;
import com.anz.demo.model.Transaction;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import java.math.BigDecimal;


public class TransactionMapperImpl implements TransactionMapper {

    private final ModelMapper modelMapper;

    public TransactionMapperImpl(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        TypeMap<Transaction, TransactionResponse> propertyMapper = this.modelMapper.createTypeMap(Transaction.class, TransactionResponse.class);
        propertyMapper.addMappings(mapper -> {
            mapper.map(src -> src.getAccount().getAccountName(), TransactionResponse::setAccountName);
            mapper.map(src -> src.getAccount().getAccountNumber(), TransactionResponse::setAccountNumber);
        });

    }

    @Override
    public TransactionResponse fromDomain(Transaction transaction) {

        return modelMapper.map(transaction, TransactionResponse.class);
    }

    @Override
    public Transaction toDomain(TransactionResponse account) {
        return modelMapper.map(account, Transaction.class);
    }
}
