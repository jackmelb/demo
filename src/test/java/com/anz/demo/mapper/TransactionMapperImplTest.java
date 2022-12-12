package com.anz.demo.mapper;

import com.anz.demo.dto.TransactionResponse;
import com.anz.demo.mapper.impl.TransactionMapperImpl;
import com.anz.demo.model.Account;
import com.anz.demo.model.Transaction;
import com.anz.demo.model.TransactionType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionMapperImplTest {
    private static final Long ACCOUNT_NUMBER = 323241211l;
    private static final String ACCOUNT_NAME = "Jack's saving account";
    private static final LocalDate VALUE_DATE = LocalDate.now();
    private static final Currency AUD = Currency.getInstance("AUD");
    private static final BigDecimal AMOUNT = new BigDecimal(100);

    private static TransactionMapper transactionMapper;

    @BeforeAll
    public static void init() {
        transactionMapper = new TransactionMapperImpl(new ModelMapper());
    }

    @Test
    public void testMapFromDomain() {
        // given
        final Account account = Account.builder()
                .accountNumber(ACCOUNT_NUMBER)
                .accountName(ACCOUNT_NAME)
                .build();
        final Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.CREDIT)
                .account(account)
                .amount(AMOUNT)
                .valueDate(VALUE_DATE)
                .currency(AUD)
                .build();

        // when
        final TransactionResponse transactionResponse = transactionMapper.fromDomain(transaction);

        // then
        assertAll("Transaction mapper",
                () ->  assertEquals(ACCOUNT_NAME, transactionResponse.getAccountName()),
                () -> assertEquals(ACCOUNT_NUMBER, transactionResponse.getAccountNumber()),
                () -> assertEquals(AMOUNT, transactionResponse.getAmount()),
                () -> assertEquals(VALUE_DATE, transactionResponse.getValueDate()),
                () -> assertEquals(AUD, transactionResponse.getCurrency()),
                () -> assertEquals(TransactionType.CREDIT, transactionResponse.getTransactionType()));
    }
}
