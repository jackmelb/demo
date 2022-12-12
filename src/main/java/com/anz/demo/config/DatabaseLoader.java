package com.anz.demo.config;

import com.anz.demo.model.*;
import com.anz.demo.repository.AccountPermissionRepository;
import com.anz.demo.repository.AccountRepository;
import com.anz.demo.repository.TransactionRepository;
import com.anz.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static com.anz.demo.security.SecurityConstants.ACCESS_ACCOUNT_ROLE;


/**
 * Load initial test data for testing only, not intended for production use
 */
@Slf4j
@Configuration
public class DatabaseLoader {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, AccountRepository accountRepository, AccountPermissionRepository accountPermissionRepository, TransactionRepository transactionRepository) {
        return args -> {
            // user from SPA
            final User user = userFactory("117533256432874867213", "jackzhang", Arrays.asList(ACCESS_ACCOUNT_ROLE));
            // user from machine to machine
            final User user1 = userFactory("RY2BesK1msUSYhg2gek4OXOSd0pWeHTy@clients", "Integration test", Arrays.asList(ACCESS_ACCOUNT_ROLE));

            for (User usr : Arrays.asList(user, user1)) {
                final Account account1 = accountFactory("Jack1", 123l, AccountType.SAVINGS, Currency.getInstance("AUD"), LocalDate.now().minusMonths(2), usr, new BigDecimal(100));
                final Account account2 = accountFactory("Jack2", 456l, AccountType.CURRENT, Currency.getInstance("AUD"), LocalDate.now().minusMonths(1), usr, new BigDecimal(200));
                final AccountPermission accountPermission = accountPermissionFactory(1l, usr, Arrays.asList(Permission.VIEW_ACCOUNT, Permission.VIEW_TRANSACTION));

                log.info("Preloading User" + userRepository.save(usr));
                log.info("Preloading Account" + accountRepository.save(account1));
                log.info("Preloading Account" + accountRepository.save(account2));
                log.info("Preloading AccountPermission" + accountPermissionRepository.save(accountPermission));

                final long amount = 10000l;
                for(int i = 0; i < 20; i++) {
                    final Transaction transaction = transactionFactory(account1, LocalDateTime.now().minusMonths(2 + i), BigDecimal.valueOf(amount + i), Currency.getInstance("AUD"), LocalDate.now());
                    log.info("Preloading transaction" + transactionRepository.save(transaction));
                }
            }
        };
    }

    private User userFactory(String userId, String username, List<String> roles) {
        return User.builder()
                .id(userId)
                .username(username)
                .roles(new HashSet<>(roles))
                .build();
    }

    private Account accountFactory(String accountName, Long accountNumber, AccountType accountType, Currency currency, LocalDate balanceDate, User user, BigDecimal openBalance) {
        return Account.builder()
                .accountName(accountName)
                .accountNumber(accountNumber)
                .accountType(accountType)
                .currency(currency)
                .balanceDate(balanceDate)
                .user(user)
                .openingAvailableBalance(openBalance)
                .build();
    }

    private AccountPermission accountPermissionFactory(Long accountId, User user, List<Permission> permissions) {
        return AccountPermission.builder()
                .accountId(accountId)
                .user(user)
                .permissions(new HashSet<>(permissions))
                .build();
    }

    private Transaction transactionFactory(Account account, LocalDateTime createTime, BigDecimal amount, Currency currency, LocalDate valueDate) {
        return Transaction.builder()
                .account(account)
                .createdTimeStamp(createTime)
                .amount(amount)
                .currency(currency)
                .lastUpdated(LocalDateTime.now())
                .valueDate(valueDate)
                .transactionType(TransactionType.CREDIT)
                .narrative("Test transaction")
                .build();
    }
}
