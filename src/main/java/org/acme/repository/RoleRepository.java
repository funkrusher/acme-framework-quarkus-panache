package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Role;

@ApplicationScoped
public class RoleRepository implements PanacheRepository<Role> {}