package org.example.movierentalsjpa.server.repository;

import org.example.movierentalsjpa.common.model.Client;

import java.util.List;

public interface ClientRepository extends GenericRepository<Client, Long> {
    List<Client> findByLastName(String name);

}
