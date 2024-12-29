package com.catdog.comerce.entity;


import com.catdog.comerce.enums.PetType;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@Entity
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "product_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "product")
public abstract class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id_product")
    private Long idProduct;

    @Column(unique = true,nullable = false)
    private String productCode;

    @Size(max=50)
    @Column(nullable = false)
    private String codeByBranch;

    @PrePersist
    public void generateProductCode() {
        if (this.productCode == null) {
            this.productCode = "PROD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
        quantitySale=0;
    }

    @NotBlank
    @Size(min = 2,max = 30)
    @Column(nullable = false)
    private String name;

    @NotNull
    @Positive
    @Column(nullable = false)
    private BigDecimal price;

    @PositiveOrZero
    @NotNull
    @Column(nullable = false)
    private Integer stock;

    @PositiveOrZero
    @NotNull
    @Column(name = "quantity_sale",nullable = false)
    private Integer quantitySale;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_category",nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "id_marca",nullable = false)
    private Brand brand;

    @Enumerated(EnumType.STRING)
    @NotNull
    private PetType petType;

    @NotBlank
    @Column(nullable = false,name = "image_product")
    private String imageProduct;
}
