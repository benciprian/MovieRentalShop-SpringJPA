package org.example.movierentalsjpa.common;

import org.example.movierentalsjpa.common.model.Client;


public interface IClientService {

    Iterable<Client> getAllClients();

    void addClient(Client client);

    Client getClientById(Long id);

    void updateClient(Client client);

    void deleteClientById(Long id);

    Iterable<Client> filterClientsByKeyword(String keyword);

}

