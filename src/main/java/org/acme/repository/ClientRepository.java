package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Client;

@ApplicationScoped
public class ClientRepository implements PanacheRepository<Client> {}
