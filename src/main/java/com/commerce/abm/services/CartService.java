package com.commerce.abm.services;

import com.commerce.abm.entities.Cart;
import com.commerce.abm.entities.Client;
import com.commerce.abm.entities.Product;
import com.commerce.abm.repositories.CartRepository;
import com.commerce.abm.repositories.ClientsRepository;
import com.commerce.abm.repositories.ProductsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ClientsRepository clientsRepository;
    @Autowired
    private ProductsRepository productRepository;

    public Cart addProductToCart(Long clientId, Long productId, Integer amount) {
      Optional<Client> client= clientsRepository.findById(clientId);
      Optional<Product> product = productRepository.findById(productId);
      if(client.isPresent() && product.isPresent()){
          Cart cart = new Cart();
          cart.setClient(client.get());
          cart.setProducts((List<Product>) product.get());
          cart.setPrice(product.get().getPrice());
          cart.setAmount(amount);
          cart.setDelivered(false);
          return cartRepository.save(cart);
      }else {
          throw new RuntimeException("Client or product not found");
      }
    }

    public void removeProductFromCart(Long cartId, Long productId) {
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            boolean productRemoved = false;
            for (Product product : cart.getProducts()) {
                if (product.getId().equals(productId)) {
                    cart.getProducts().remove(product);
                    productRemoved = true;
                    break;                }
            }
            if (productRemoved) {
                cartRepository.save(cart);
            } else {
                throw new EntityNotFoundException("Product not found in the cart");
            }
        } else {
            throw new EntityNotFoundException("Cart not found");
        }
    }


    public List<Cart> findByClientAndDelivered(Long clientId) {
     List<Cart>carts =cartRepository.findByClientAndDelivered(clientId, false);
     if (carts.isEmpty()){
         throw new RuntimeException("Cart not found");
     } else {
         return carts;
     }
    }
}