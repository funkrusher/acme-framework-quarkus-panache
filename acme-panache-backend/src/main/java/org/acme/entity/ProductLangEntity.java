package org.acme.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.persistence.*;

import java.util.List;

@Entity(name = "product_lang")
@Cacheable
public class ProductLangEntity extends PanacheEntityBase {

    @EmbeddedId
    public ProductLangEntityId id;

    // uses productId field in EmbeddedId Datatype
    @ManyToOne
    @JoinColumn(name = "productId")
    @MapsId("productId")
    private ProductEntity product;

    // uses langId field in EmbeddedId Datatype
    @ManyToOne
    @JoinColumn(name = "langId")
    @MapsId("langId")
    private LangEntity lang;

    public String name;

    @Column(length = 1024)
    public String description;

}
