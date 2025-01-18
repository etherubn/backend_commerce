package com.catdog.comerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "user_data")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    @EqualsAndHashCode.Include
    private Long idUser;

    @Size(min = 5, max = 50)
    private String address;

    @Size(max = 30)
    private String name;

    @Size(max = 30)
    @Column(name = "last_name")
    private String lastName;


    @Column(unique = true)
    @Size(min = 8,max = 8)
    private String dni;

    @Email
    @NotBlank
    @Column(unique = true,nullable = false)
    private String email;

    @NotBlank
    @Size(min = 4,max = 12)
    @Column(unique = true,nullable = false)
    private String username;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    private String password;

    @Column(updatable = false,nullable = false,name = "creation_date")
    private LocalDateTime creationDate;

    @Column(nullable = false,name = "enable_buyer")
    private boolean enableBuyer;

    @PrePersist
    public void setBeforePersist(){
        creationDate = LocalDateTime.now();
        purchaseAmount = 0;
        enableBuyer = username.equals("admin");
        username = username.toLowerCase();
        email = email.toLowerCase();
    }

    @PreUpdate
    public void setBeforeUpdate(){
        username = username.toLowerCase();
        lastName = lastName.toLowerCase();
    }


    @PositiveOrZero
    @Column(name = "purchase_amount")
    private Integer purchaseAmount;

    @Column(name = "is_enabled")
    private boolean isEnabled = true;
    @Column(name = "account_no_expired")
    private boolean accountNoExpired = true;
    @Column(name = "account_no_locked")
    private boolean accountNoLocked = true;
    @Column(name = "credential_no_expired")
    private boolean credentialNoExpired = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> role;
}
