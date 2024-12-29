package com.catdog.comerce.entity;

import com.catdog.comerce.entity.key.SellingProductKey;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data

@AllArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "selling_product")
public class SellingProduct {

    @EmbeddedId
    @EqualsAndHashCode.Include
    private SellingProductKey idSellingProduct;


    @Positive
    @Column(nullable = false,updatable = false)
    private Integer quantity;

    @Positive
    @Column(nullable = false,updatable = false)
    private BigDecimal subtotal;

    public SellingProduct() {
        this.idSellingProduct = new SellingProductKey();
    }

    public SellingProduct(Integer quantity, Selling selling, Product product) {
        this.quantity = quantity;
        this.selling = selling;
        this.product = product;
        this.idSellingProduct =new SellingProductKey(selling.getIdSelling(), product.getIdProduct());
    }

    public void setSelling(Selling selling) {
        this.selling = selling;
        this.idSellingProduct.setIdSelling(selling.getIdSelling());
    }

    public void setProduct(Product product) {
        this.product = product;
        this.idSellingProduct.setIdProduct(product.getIdProduct());
    }

    @ManyToOne
    @JoinColumn(name = "id_selling",nullable = false)
    @MapsId(value = "idSelling")
    private Selling selling;

    @ManyToOne
    @JoinColumn(name = "id_product",nullable = false)
    @MapsId(value = "idProduct")
    private Product product;
}
