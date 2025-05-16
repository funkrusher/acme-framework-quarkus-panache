package org.acme.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductLangId implements Serializable {
    public Long productId;
    public Integer langId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProductLangId entity = (ProductLangId) o;
        return Objects.equals(this.productId, entity.productId) &&
                Objects.equals(this.langId, entity.langId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, langId);
    }

}