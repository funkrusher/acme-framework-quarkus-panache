package org.acme.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "product_lang")
public class ProductLang extends PanacheEntityBase {
    @EmbeddedId
    public ProductLangId id;

    @MapsId("productId")
    @ManyToOne
    @JoinColumn(name = "productId")
    @JsonIgnoreProperties("productLangs") // ⛔ Don't serialize the list back
    public Product product;

    @MapsId("langId")
    @ManyToOne
    @JoinColumn(name = "langId")
    public Lang lang;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    public String description;
}