package com.anz.demo.mapper;


import com.anz.demo.dto.TransactionResponse;
import com.anz.demo.model.Transaction;

public interface TransactionMapper {

   TransactionResponse fromDomain(Transaction transaction);
   Transaction toDomain(TransactionResponse account);
}
