package org.example.movierentalsjpa.server.service;

import org.example.movierentalsjpa.common.IClientService;
import org.example.movierentalsjpa.common.model.Client;
import org.example.movierentalsjpa.common.model.Movie;
import org.example.movierentalsjpa.common.model.exceptions.MovieRentalsException;
import org.example.movierentalsjpa.server.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SClientServiceImpl implements IClientService {
    private static final Logger log = LoggerFactory.getLogger(SClientServiceImpl.class);

    @Autowired
    ClientRepository clientRepository;


    @Override
    public List<Client> getAllClients() {
        log.trace("getAllClients --- method entered");

        List<Client> result = clientRepository.findAll();

        log.trace("getAllClients: result={}", result);

        return result;
    }

    @Override
    public void addClient(Client client) {
        log.trace("addClient: client={}", client);

        Client result = clientRepository.save(client);

        log.trace("addClient: client={}", result);
    }

    @Override
    public Client getClientById(Long id) {
        log.trace("getClientById --- method entered");

        Client result = clientRepository.findById(id).orElseThrow(() -> new MovieRentalsException("Client not found with ID: " + id));

        log.trace("getClientById: client={}", result);

        return result;
    }

    @Override
    public void updateClient(Client client) {
        log.trace("updateClient --- method entered");
        if(!clientRepository.existsById(client.getId())){
            log.error("client with ID {} not found", client.getId());
            throw new MovieRentalsException("Client not found with ID " + client.getId());
        }
        Client result = clientRepository.save(client);
        log.trace("updateClient: client{}", result);
    }

    @Override
    public void deleteClientById(Long id) {
        log.trace("deleteClient --- method entered");
        if(!clientRepository.existsById(id)){
            log.error("client with ID {} not found", id);
            throw new MovieRentalsException("Client not found with ID " + id);
        }
        try {
            clientRepository.deleteById(id);
            log.trace("client{} successfully deleted", id);
        } catch (DataAccessException e){
            throw new MovieRentalsException("Cannot delete Client ID{" + id + "} because it appears in the Rental Reports!!!");
        }
    }

//    @Override
//    public Iterable<Client> filterClientsByKeyword(String keyword) {
//        log.trace("filterClientsByKeyword --- method entered");
//        try {
//            Iterable<Client> clients = clientRepository.findAll();
//            Set<Client> result = StreamSupport.stream(clients.spliterator(), false)
//                    .filter(client -> client.getFirstName().toLowerCase().contains(keyword.toLowerCase())
//                           || client.getLastName().toLowerCase().contains(keyword.toLowerCase()))
//                    .collect(Collectors.toSet());
//           log.trace("filterClientsByKeyword: result{}", result);
//            return result;
//        } catch (MovieRentalsException e) {
//           throw new MovieRentalsException(e.getMessage());
//        }
//    }

    @Override
    public Set<Client> filterClientsByKeyword(String name){
       log.trace("filterClientsByKeyword --- method entered");
        List<Client> resultList = clientRepository.findByLastName(name);
        Set<Client> result = new HashSet<>(resultList);
        log.trace("filterClientsByKeyword: result{}", result);
       return result;
   }
}
