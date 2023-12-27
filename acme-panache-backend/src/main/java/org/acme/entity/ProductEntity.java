package org.acme.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ForeignKey;
import org.hibernate.annotations.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity(name = "product")
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
public class ProductEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    public Long productId;

    public Integer clientId;
    public BigDecimal price;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
    public Boolean deleted;

    // i must use fetch-type eager to Force the resolvement
    // of the lang-entries in case of the panache-query-params solution,
    // as in that case the transaction is already finished when it tries to load the stuff.
    // note: i hate this magic, and also it seems hibernate resolves the lang-entries via single SQL-statements
    // for each found entry (verify this). This would slow down complex data from complex table-relations very much if that is the case.
    @OneToMany(mappedBy = "product",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    public List<ProductLangEntity> langs = new ArrayList<>();
}
