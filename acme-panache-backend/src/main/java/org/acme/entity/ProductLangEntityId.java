package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Embeddable
public class ProductLangEntityId implements Serializable {
    @Column(name = "productId")
    public Long productId;


    @Column(name = "langId")
    public Integer langId;
}
