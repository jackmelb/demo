package com.anz.demo.service;


import com.anz.demo.model.Transaction;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionService {
    List<Transaction> getTransactionsByAccountId(Long accountId, Pageable pageable);
}
