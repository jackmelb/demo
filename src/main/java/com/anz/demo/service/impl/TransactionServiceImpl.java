package com.anz.demo.service.impl;

import com.anz.demo.model.Transaction;
import com.anz.demo.repository.TransactionRepository;
import com.anz.demo.service.TransactionService;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(final TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Transaction> getTransactionsByAccountId(Long accountId, Pageable pageable) {
        return transactionRepository.findAllByAccountId(accountId, pageable);
    }
}
