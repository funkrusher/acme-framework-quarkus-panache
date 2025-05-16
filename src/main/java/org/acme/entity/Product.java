package org.acme.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
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
public class Product extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long productId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "clientId")
    public Client client;

    @Column(nullable = false, precision = 10, scale = 2)
    public BigDecimal price;

    @Column(nullable = false)
    public LocalDateTime createdAt;

    @Column(nullable = false)
    public LocalDateTime updatedAt;

    @Column(nullable = false)
    public boolean deleted;

    @JsonIgnoreProperties("product") // ⛔ Don't serialize back to product
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<ProductLang> productLangs;
}