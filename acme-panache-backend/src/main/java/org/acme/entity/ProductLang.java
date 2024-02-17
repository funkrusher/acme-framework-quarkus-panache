package org.acme.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "product_lang")
public class ProductLang {
    @EmbeddedId
    private ProductLangId id;

    // From Codegenerator, but can not be used as it would create an endless-recursion
    // when used with a PanacheQuery on Product
//    @MapsId("productId")
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JoinColumn(name = "productId", nullable = false)
    @ManyToOne
    @JoinColumn(name = "productId")
    @MapsId("productId")
    private Product product;

    @MapsId("langId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "langId", nullable = false)
    private Lang lang;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Lob
    @Column(columnDefinition = "text", name = "description", nullable = false)
    private String description;

    public ProductLangId getId() {
        return id;
    }

    public void setId(ProductLangId id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Lang getLang() {
        return lang;
    }

    public void setLang(Lang lang) {
        this.lang = lang;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}