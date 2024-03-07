package org.example.movierentalsjpa.client.service;

import org.example.movierentalsjpa.common.IClientService;

import org.example.movierentalsjpa.common.model.Client;
import org.example.movierentalsjpa.common.model.exceptions.MovieRentalsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class CClientServiceImpl implements IClientService {

    private IClientService clientService;

    @Override
    public Iterable<Client> getAllClients() throws MovieRentalsException {
        return clientService.getAllClients();
    }

    @Override
    public void addClient(Client client) throws MovieRentalsException  {

        clientService.addClient(client);
    }

    @Override
    public Client getClientById(Long id) throws MovieRentalsException {
        return clientService.getClientById(id);
    }


    @Override
    public void updateClient(Client client) throws MovieRentalsException {
        clientService.updateClient(client);

    }

    @Override
    public void deleteClientById(Long id) throws MovieRentalsException  {
        clientService.deleteClientById(id);

    }

    @Override
    public Iterable<Client> filterClientsByKeyword(String keyword) throws MovieRentalsException {
        return clientService.filterClientsByKeyword(keyword);
    }
}
