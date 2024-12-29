package com.catdog.comerce.entity.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SellingProductKey implements Serializable {

    @Column(name = "id_selling")
    private Long idSelling;

    @Column(name = "id_product")
    private Long idProduct;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof SellingProductKey that)) return false;
        return Objects.equals(idSelling, that.idSelling) && Objects.equals(idProduct, that.idProduct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSelling, idProduct);
    }
}
