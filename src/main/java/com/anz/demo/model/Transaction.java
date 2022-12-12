package com.anz.demo.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate valueDate;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    private LocalDateTime createdTimeStamp;

    private LocalDateTime lastUpdated;

    private Currency currency;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private String narrative;
}
