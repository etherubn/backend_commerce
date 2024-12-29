package com.catdog.comerce.entity;

import com.catdog.comerce.enums.CategoryType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id_category")
    private Long idCategory;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false,unique = true)
    private CategoryType type;
}
