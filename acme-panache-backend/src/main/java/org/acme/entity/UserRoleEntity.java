package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity(name = "user_role")
@Cacheable
public class UserRoleEntity extends PanacheEntityBase {
    @Id
    public Integer userId;
    @Id
    public String roleId;
}
