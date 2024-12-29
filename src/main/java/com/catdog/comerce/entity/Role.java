package com.catdog.comerce.entity;

import com.catdog.comerce.enums.RoleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Long idRole;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private RoleType type;
}
