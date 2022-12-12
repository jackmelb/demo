package com.anz.demo.repository;

import com.anz.demo.model.Account;
import com.anz.demo.model.AccountPermission;
import com.anz.demo.model.Permission;
import com.anz.demo.model.User;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Testcontainers
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = {AccountRepositoryTestContainerIntegrationTest.DataSourceInitializer.class})
public class AccountRepositoryTestContainerIntegrationTest {

    private static final String USER_ID = "323241211";
    private static final String UNKNOWN_USER_ID = "323241212";

    private static final Long ACCOUNT_NUMBER_ONE = 585309209l;
    private static final Long ACCOUNT_NUMBER_TWO = 585309210l;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountPermissionRepository accountPermissionRepository;

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:12.9-alpine");

    public static class DataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.test.database.replace=none",
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            );
        }
    }

    @Test
    @Transactional
    public void givenAccountsInDB_WhenSearchAccountByUserId_ItShouldReturnAccounts(){
        insertAccounts();
        List<Account> accounts = accountRepository.findAllByUserId(USER_ID, PageRequest.of(0,2, Sort.by("id")));
        Assert.assertEquals(accounts.size(), 2);
        Assert.assertEquals(accounts.get(0).getAccountNumber(), ACCOUNT_NUMBER_ONE);
        Assert.assertEquals(accounts.get(1).getAccountNumber(), ACCOUNT_NUMBER_TWO);
    }

    @Test
    @Transactional
    public void givenAccountsInDB_WhenSearchAccountByUserId_ItShouldNotReturnAccountsForUnknownUser(){
        insertAccounts();
        List<Account> accounts = accountRepository.findAllByUserId(UNKNOWN_USER_ID, PageRequest.of(0,2, Sort.by("id")));
        Assert.assertEquals(accounts.size(), 0);
    }

    private void insertAccounts() {
       final User user = User.builder().id(USER_ID).roles(new HashSet<>(Arrays.asList("test"))).build();
        userRepository.save(user);
        accountRepository.save(Account.builder().accountNumber(ACCOUNT_NUMBER_ONE).accountName("jack one").user(user).build());
        accountRepository.save(Account.builder().accountNumber(ACCOUNT_NUMBER_TWO).accountName("jack two").user(user).build());
        final AccountPermission accountPermission = AccountPermission.builder().accountId(1l).user(user).permissions(new HashSet<>(Arrays.asList(Permission.VIEW_ACCOUNT, Permission.VIEW_TRANSACTION))).build();
        accountPermissionRepository.save(accountPermission);
    }
}
