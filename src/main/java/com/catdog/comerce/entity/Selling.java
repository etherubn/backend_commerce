package com.catdog.comerce.entity;

import com.catdog.comerce.enums.SellingState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "selling")
public class Selling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_selling")
    @EqualsAndHashCode.Include
    private Long idSelling;

    @NotNull
    @Column(updatable = false,nullable = false,name = "order_date")
    private LocalDateTime creationSelling;

    @PrePersist
    public void createDateOrder(){
        creationSelling= LocalDateTime.now();
        sellingState= SellingState.PROCESSING_ORDER;
    }

    @NotNull
    @Column(name = "order_state",nullable = false)
    @Enumerated(EnumType.STRING)
    private SellingState sellingState;



    @OneToMany(mappedBy = "selling",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<SellingProduct>  sellingProducts = new ArrayList<>();

    @PositiveOrZero
    private BigDecimal total= BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "id_user",nullable = false,updatable = false)
    private User user;

    @PositiveOrZero
    @Column(name = "shipping_price",nullable = false)
    private BigDecimal shippingPrice;

}
