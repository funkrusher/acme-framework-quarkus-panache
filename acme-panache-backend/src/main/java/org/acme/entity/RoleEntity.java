package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity(name = "role")
@Cacheable
public class RoleEntity extends PanacheEntityBase {
    @Id
    @Column(name = "roleId")
    public String roleId;
}
