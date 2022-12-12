package com.anz.demo.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account {
    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountNumber;

    private String accountName;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @OneToMany(mappedBy = "account",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Transaction> transactions;

    private LocalDate balanceDate;

    private Currency currency;

    private BigDecimal openingAvailableBalance;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
