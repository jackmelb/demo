package com.anz.demo.repository;

import com.anz.demo.model.Account;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {
    List<Account> findAllByUserId(String userId, Pageable pageable);
}
