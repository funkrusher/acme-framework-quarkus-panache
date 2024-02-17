package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Product;
import org.hibernate.annotations.*;

import java.math.BigDecimal;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {

}
