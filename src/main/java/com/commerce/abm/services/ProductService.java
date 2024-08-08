package com.commerce.abm.services;

import com.commerce.abm.entities.Cart;
import com.commerce.abm.entities.Client;
import com.commerce.abm.entities.Product;
import com.commerce.abm.repositories.CartRepository;
import com.commerce.abm.repositories.ClientsRepository;
import com.commerce.abm.repositories.ProductsRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {


    @Autowired private ProductsRepository productsRepository;
    @Autowired private ClientsRepository clientsRepository;
    @Autowired private CartRepository cartRepository;


    public Product save (Product product){
        return productsRepository.save(product);
    }

    public List<Product> findAll() {
        return productsRepository.findAll();
    }

    public  Optional<Product> findByID(Long id){
        return  productsRepository.findById(id);
    }


    public void deleteById(Long id ) {
        productsRepository.deleteById(id);
    }

    public Product buyProduct(Long productId, Long clientId) throws Exception {
        // Buscar el producto por ID
        Optional<Product> productOptional = productsRepository.findById(productId);
        if (!productOptional.isPresent()) {
            throw new Exception("Product not found with id " + productId);
        }
        // Buscar el cliente por ID
        Optional<Client> clientOptional = clientsRepository.findById(clientId);
        if (!clientOptional.isPresent()) {
            throw new Exception("Client not found with id: " + clientId);
        }

        Product foundProduct = productOptional.get();
        Client foundClient = clientOptional.get();

        // Buscar el carrito del cliente o crear uno nuevo si no existe
        Cart cart = cartRepository.findById(clientId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setClient(foundClient);
                    newCart.setPrice(0.0); // Establece un precio inicial o realiza el ajuste según tu lógica
                    newCart.setAmount(0); // Establece una cantidad inicial o realiza el ajuste según tu lógica
                    newCart.setDelivered(false); // Establece un estado inicial si es necesario
                    return cartRepository.save(newCart);
                });

        // Asignar el carrito al producto
        foundProduct.setCart(cart);

        // Guardar el producto actualizado con el carrito asignado
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
