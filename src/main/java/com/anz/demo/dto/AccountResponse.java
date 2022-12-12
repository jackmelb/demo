package com.anz.demo.dto;

import com.anz.demo.model.AccountType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

@Data
public class AccountResponse {
    private long id;

    private long accountNumber;

    private String accountName;

    private AccountType accountType;

    private LocalDate balanceDate;

    private Currency currency;

    private BigDecimal openingAvailableBalance;
}
