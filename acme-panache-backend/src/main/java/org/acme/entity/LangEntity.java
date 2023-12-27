package org.acme.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "lang")
@Cacheable
public class LangEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "langId")
    public Integer langId;
    public String code;
    public String description;

    @OneToMany(mappedBy = "lang",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    public List<ProductLangEntity> products = new ArrayList<>();
}
