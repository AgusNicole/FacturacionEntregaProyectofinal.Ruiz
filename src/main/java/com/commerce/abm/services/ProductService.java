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


  public Optional<Product> getOneProduct(Long id){
      return productsRepository.findById(id);
   }

    public void deleteById(Long id) {
        productsRepository.deleteById(id);
    }

    public Product buyProduct(Long productId, Long clientId) throws Exception {
        Optional<Product> productOptional = productsRepository.findById(productId);
        if (!productOptional.isPresent()) {
            throw new Exception("Product not found with id " + productId);
        }
        Optional<Client> clientOptional = clientsRepository.findById(clientId);
        if (!clientOptional.isPresent()) {
            throw new Exception("Client not found with id: " + clientId);
        }

        Product foundProduct = productOptional.get();
        Client foundClient = clientOptional.get();
        // Buscar el carrito del cliente o crear uno nuevo si no existe
        List<Cart> clientCarts = cartRepository.findByClientId(foundClient.getId());
        Cart cart = clientCarts.stream()
                .filter(c -> !c.isDelivered())
                .findFirst()
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setClient(foundClient);
                    newCart.setPrice(0.0); // Ajusta el precio según tu lógica
                    newCart.setAmount(0); // Ajusta la cantidad según tu lógica
                    newCart.setDelivered(false);
                    return cartRepository.save(newCart);
                });

        // Agregar el producto al carrito
        cart.setProduct(foundProduct);
        cart.setAmount(cart.getAmount() + 1); // Incrementa la cantidad o ajusta según la lógica
        cart.setPrice(cart.getPrice() + foundProduct.getPrice()); // Ajusta el precio del carrito

        // Guardar el carrito actualizado
        cartRepository.save(cart);

        return foundProduct;
    }


    public Product update(Long id, Product updatedProduct) {
        Optional<Product> existingProduct = productsRepository.findById(id);
        if (existingProduct.isPresent()) {
            updatedProduct.setId(id);
            return productsRepository.save(updatedProduct);
        } else {
            throw new EntityNotFoundException("Product not found: " + id);
        }
    }





}
