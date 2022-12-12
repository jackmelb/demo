package com.anz.demo.config;


import com.anz.demo.mapper.AccountMapper;
import com.anz.demo.mapper.TransactionMapper;
import com.anz.demo.mapper.impl.AccountMapperImpl;
import com.anz.demo.mapper.impl.TransactionMapperImpl;
import com.anz.demo.repository.AccountPermissionRepository;
import com.anz.demo.repository.AccountRepository;
import com.anz.demo.repository.TransactionRepository;
import com.anz.demo.repository.UserRepository;
import com.anz.demo.security.AccountPermissionEvaluator;
import com.anz.demo.service.AccountService;
import com.anz.demo.service.TransactionService;
import com.anz.demo.service.UserService;
import com.anz.demo.service.impl.AccountServiceImpl;
import com.anz.demo.service.impl.TransactionServiceImpl;
import com.anz.demo.service.impl.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public AccountService accountService(AccountRepository accountRepository) {
        return new AccountServiceImpl(accountRepository);
    }

    @Bean
    public TransactionService transactionService(TransactionRepository transactionRepository) {
        return new TransactionServiceImpl(transactionRepository);
    }

    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserServiceImpl(userRepository);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public AccountMapper accountMapper(ModelMapper modelMapper) {
        return new AccountMapperImpl(modelMapper);
    }

    @Bean
    public TransactionMapper transactionMapper(ModelMapper modelMapper) {
        return new TransactionMapperImpl(modelMapper);
    }

    @Bean
    public AccountPermissionEvaluator accountPermissionEvaluator(AccountPermissionRepository accountPermissionRepository) {
        return new AccountPermissionEvaluator(accountPermissionRepository);
    }
}
