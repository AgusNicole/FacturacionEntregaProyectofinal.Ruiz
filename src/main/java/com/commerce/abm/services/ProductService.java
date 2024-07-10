package com.commerce.abm.services;

import com.commerce.abm.entities.Client;
import com.commerce.abm.entities.Product;
import com.commerce.abm.repositories.ClientsRepository;
import com.commerce.abm.repositories.ProductsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired private ClientsRepository clientsRepository;
    @Autowired   private  ProductsRepository productsRepository;

    public List<Product> findAll() {
        return productsRepository.findAll();
    }

    public  Optional<Product> findByID(Long id){
        return  productsRepository.findById(id);
    }

    public Product save (Product product){
        return productsRepository.save(product);
    }

    public void deleteById(Long id ) {
        productsRepository.deleteById(id);
    }

    public Product buyProduct(Long productId, Long clientId) throws Exception {
        Optional<Product> product = productsRepository.findById(productId);
        if (!product.isPresent()) {
            throw new Exception("Product not found with id " + productId);
        }

        Optional<Client> client = clientsRepository.findById(clientId);
        if (!client.isPresent()) {
            throw new Exception("Client not found with id: " + clientId);
        }

        Product foundProduct = product.get();
        Client foundClient = client.get();

        foundProduct.setClient(foundClient); //asigna el cliente encontrado

        // Guarda el producto actualizado con el cliente asignado
        productsRepository.save(foundProduct);

        return foundProduct;
    }


    public Product update(Long id, Product updatedProduct) {
        Optional<Product> existingProduct = productsRepository.findById(id);

        if (existingProduct.isPresent()) {
            updatedProduct.setId(id); // Asegura que el ID del producto a actualizar sea el correcto
            return productsRepository.save(updatedProduct);
        } else {
            throw new EntityNotFoundException("Producto no encontrado con ID: " + id);
        }
    }

}
