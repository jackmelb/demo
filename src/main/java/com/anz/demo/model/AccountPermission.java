package com.anz.demo.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account_permission")
public class AccountPermission {
    @Id
    @Column(name = "account_permission_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ElementCollection(targetClass=Permission.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Permission> permissions;
}
