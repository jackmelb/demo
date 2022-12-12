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
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    private String id;

    private String username;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Account> accounts;

    @ElementCollection(targetClass=String.class, fetch = FetchType.EAGER)
    private Set<String> roles;
}
