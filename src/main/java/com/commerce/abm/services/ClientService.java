package com.commerce.abm.services;

import com.commerce.abm.entities.Client;
import com.commerce.abm.entities.Product;
import com.commerce.abm.repositories.ClientsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {


    @Autowired
    private ClientsRepository clientsRepository;

    public Client saveClient (Client client) {
        return clientsRepository.save(client);
    }

    public List<Client> getClients() {
        return clientsRepository.findAll();
    }

    public Optional<Client> getOneClient(Long id) {
        Optional<Client> cliente = clientsRepository.findById(id);
        return cliente;
    }

    public void deleteOneClient(Long id) {
        clientsRepository.deleteById(id);
    }





}
