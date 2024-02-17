package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.UserRole;

@ApplicationScoped
public class UserRoleRepository implements PanacheRepository<UserRole> {

}
