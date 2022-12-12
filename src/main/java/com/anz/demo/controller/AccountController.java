package com.anz.demo.controller;

import com.anz.demo.dto.AccountResponse;
import com.anz.demo.dto.TransactionResponse;
import com.anz.demo.mapper.AccountMapper;
import com.anz.demo.mapper.TransactionMapper;
import com.anz.demo.model.Account;
import com.anz.demo.model.Transaction;
import com.anz.demo.service.AccountService;
import com.anz.demo.service.TransactionService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(AccountController.ROOT_PATH)
public class AccountController {
    public static final String ROOT_PATH = "/accounts";

    private final AccountService accountService;

    private final AccountMapper accountMapper;

    private final TransactionService transactionService;

    private final TransactionMapper transactionMapper;

    public AccountController(final AccountService accountService, final AccountMapper accountMapper, final TransactionService transactionService, final TransactionMapper transactionMapper) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
    }

    @GetMapping
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all accounts for current user"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(responseCode = "403", description = "User does not have required roles"),
            @ApiResponse(responseCode = "404", description = "No account is found for current user")

    })
    @PreAuthorize("hasAuthority('access-account')")
    public ResponseEntity getAccountsForCurrentUser(@RequestParam(defaultValue = "0") Integer page,
                                                    @RequestParam(defaultValue = "10") Integer size,
                                                    @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        List<Account> accounts = accountService.getAccountsByUserId(getAuthenticatedUser(), pageable);
        List<AccountResponse> accountResponses = accounts.stream().map(accountMapper::fromDomain).collect(Collectors.toList());
        return new ResponseEntity(accountResponses, accountResponses.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{accountId}/transactions")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all transactions for given account"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(responseCode = "403", description = "User does not have required roles/permissions"),
            @ApiResponse(responseCode = "404", description = "No transaction is found for given account")

    })
    @PreAuthorize("hasAuthority('access-account') and hasPermission(#accountId, 'account', 'VIEW_TRANSACTION')")
    public ResponseEntity getTransactionsByAccount(@RequestParam(defaultValue = "0") Integer page,
                                                   @RequestParam(defaultValue = "10") Integer size,
                                                   @RequestParam(defaultValue = "id") String sortBy,
                                                   @Valid @PathVariable("accountId") Long accountId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        List<Transaction> transactions = transactionService.getTransactionsByAccountId(accountId, pageable);
        List<TransactionResponse> transactionResponses = transactions.stream().map(transactionMapper::fromDomain).collect(Collectors.toList());
        return new ResponseEntity(transactionResponses, transactionResponses.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    private String getAuthenticatedUser() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
