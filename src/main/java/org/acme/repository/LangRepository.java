package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Lang;

@ApplicationScoped
public class LangRepository implements PanacheRepository<Lang> {}
