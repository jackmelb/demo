package com.anz.demo.dto;

import com.anz.demo.model.TransactionType;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

@Data
public class TransactionResponse {
    private Long id;

    private Long accountNumber;

    private String accountName;

    private LocalDate valueDate;

    private Currency currency;

    private BigDecimal amount;

    private TransactionType transactionType;

    private String narrative;
}
