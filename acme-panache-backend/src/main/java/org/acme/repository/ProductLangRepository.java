package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.ProductLang;

@ApplicationScoped
public class ProductLangRepository implements PanacheRepository<ProductLang> {

}
