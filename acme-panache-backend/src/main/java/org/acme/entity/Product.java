package org.acme.entity;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "product")
@FilterDefs({
        @FilterDef(name = "product.productId.equal", defaultCondition = "productId = :productId", parameters = @ParamDef(name = "productId", type = Long.class)),
        @FilterDef(name = "product.productId.greater", defaultCondition = "productId > :productId", parameters = @ParamDef(name = "productId", type = Long.class)),
        @FilterDef(name = "product.productId.lesser", defaultCondition = "productId < :productId", parameters = @ParamDef(name = "productId", type = Long.class)),
        @FilterDef(name = "product.price.greater_equal", defaultCondition = "price >= :price", parameters = @ParamDef(name = "price", type = BigDecimal.class)),
        @FilterDef(name = "product.price.lesser_equal", defaultCondition = "price <= :price", parameters = @ParamDef(name = "price", type = BigDecimal.class)),
        @FilterDef(name = "product_lang.name.like", parameters = @ParamDef(name = "name", type = String.class)),
})
@Filters({
        @Filter(name = "product.productId.equal"),
        @Filter(name = "product.productId.greater"),
        @Filter(name = "product.productId.lesser"),
        @Filter(name = "product.price.greater_equal"),
        @Filter(name = "product.price.lesser_equal"),
        @Filter(name = "product_lang.name.like", condition = "exists (select 1 from product_lang l where l.productId = productId and l.name = :name)")
})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId", nullable = false)
    private Long productId;

    @NotNull
    @Column(name = "clientId", nullable = false)
    private Integer client;

    @NotNull
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @NotNull
    @Column(name = "createdAt", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updatedAt", nullable = false)
    private Instant updatedAt;

    @NotNull
    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    // i must use fetch-type eager to Force the resolvement
    // of the lang-entries in case of the panache-query-params solution,
    // as in that case the transaction is already finished when it tries to load the stuff.
    // note: i hate this magic, and also it seems hibernate resolves the lang-entries via single SQL-statements
    // for each found entry (verify this). This would slow down complex data from complex table-relations very much if that is the case.
    @OneToMany(mappedBy = "product",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<ProductLang> productLangs = new LinkedHashSet<>();

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Set<ProductLang> getProductLangs() {
        return productLangs;
    }

    public void setProductLangs(Set<ProductLang> productLangs) {
        this.productLangs = productLangs;
    }

}