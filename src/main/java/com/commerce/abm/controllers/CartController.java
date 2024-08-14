package com.commerce.abm.controllers;

import com.commerce.abm.entities.Cart;
import com.commerce.abm.entities.Product;
import com.commerce.abm.services.CartService;
import com.commerce.abm.services.ClientService;
import com.commerce.abm.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/{clientId}/{productId}/{quantity}")
    public ResponseEntity<Cart> addProductToCart(@PathVariable Long clientId,
                                                 @PathVariable Long productId,
                                                 @PathVariable Integer quantity) {
        try {
            Cart cart = cartService.addProductToCart(clientId, productId, quantity);
            return ResponseEntity.status(HttpStatus.CREATED).body(cart);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{cartId}/products/{productId}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
        try {
            cartService.removeProductFromCart(cartId, productId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<List<Product>> getProductsInCart(@PathVariable Long clientId) {
        try {
            List<Cart> carts = cartService.findByClientIdAndDelivered(clientId);
            List<Product> products = carts.stream()
                    .map(Cart::getProduct)
                    .collect(Collectors.toList());
            if (!products.isEmpty()) {
                return ResponseEntity.ok(products);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("Endpoint is working");
    }
}
