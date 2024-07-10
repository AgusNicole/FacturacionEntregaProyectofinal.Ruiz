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
    private ClientsRepository repository;

    public Client saveClient (Client client) {
      return repository.save(client);
    }

    public List<Client> getClients() {
        return repository.findAll();
    }

    public Optional<Client> getOneClient(Long id) {
        Optional<Client> cliente = repository.findById(id);
        return cliente;
    }

    public void deleteOneClient(Long id) {
        repository.deleteById(id);
    }

    public Client addProductToCart(Long clientId, Product productToAdd) {
        Optional<Client> optionalClient = repository.findById(clientId);

        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();

            // Agregar el producto al carrito del cliente
            client.getProducts().add(productToAdd);

            // Guardar y devolver el cliente actualizado
            return repository.save(client);
        } else {
            throw new EntityNotFoundException("Cliente no encontrado con ID: " + clientId);
        }
    }

    public Client removeProductFromCart(Long clientId, Long productIdToRemove) {
        Optional<Client> optionalClient = repository.findById(clientId);

        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();

            // Remover el producto del carrito del cliente
            client.getProducts().removeIf(product -> product.getId().equals(productIdToRemove));

            // Guardar y devolver el cliente actualizado
            return repository.save(client);
        } else {
            throw new EntityNotFoundException("Cliente no encontrado con ID: " + clientId);
        }
    }


}
