package com.anz.demo.repository;

import com.anz.demo.model.AccountPermission;;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AccountPermissionRepository extends PagingAndSortingRepository<AccountPermission, Long> {
    List<AccountPermission> findAllByUserId(String userId);
}