package com.anz.demo.security;

import com.anz.demo.model.AccountPermission;
import com.anz.demo.repository.AccountPermissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.anz.demo.security.SecurityConstants.ACCOUNT_TARGET;

@Slf4j
public class AccountPermissionEvaluator implements PermissionEvaluator {

    private final AccountPermissionRepository accountPermissionRepository;

    public AccountPermissionEvaluator(final AccountPermissionRepository accountPermissionRepository) {
        this.accountPermissionRepository = accountPermissionRepository;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        throw new RuntimeException("Not Supported");
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
       log.info("Checking permission for target {}, type {}, permission {}", targetId, targetType, permission);
       if(authentication == null || targetId == null || !StringUtils.hasText(targetType) || permission == null) {
           log.error("Unable to check permission due to missing parameters");
           return false;
       }
       if(!authentication.isAuthenticated()) {
           return false;
       }

       List<AccountPermission> permissions = accountPermissionRepository.findAllByUserId(authentication.getPrincipal().toString());

       return checkPermission(permissions, targetType, permission, targetId);
    }

    private boolean checkPermission(List<AccountPermission> accountPermissions, String targetType, Object permission, Serializable targetId) {
        if(ACCOUNT_TARGET.equalsIgnoreCase(targetType)) {
            Optional<AccountPermission> accountPermissionOptional = accountPermissions.stream().filter(accountPermission ->
                    accountPermission.getPermissions().stream().map(Enum::name).collect(Collectors.toList()).contains(permission)
                    && accountPermission.getAccountId().equals(targetId)).findFirst();
            return accountPermissionOptional.isPresent();
        }
        return false;
    }
}
