package org.acme.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductLangId implements Serializable {
    private static final long serialVersionUID = -8477703267419440413L;
    @NotNull
    @Column(name = "productId", nullable = false)
    private Long productId;

    @NotNull
    @Column(name = "langId", nullable = false)
    private Integer langId;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getLangId() {
        return langId;
    }

    public void setLangId(Integer langId) {
        this.langId = langId;
    }

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