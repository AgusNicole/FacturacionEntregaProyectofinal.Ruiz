package com.commerce.abm.controllers;

import com.commerce.abm.entities.Cart;
import com.commerce.abm.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @Operation(summary = "Add product to cart", description = "Adds a product to the client's cart with the specified amount")
    @ApiResponse(responseCode = "201", description = "Product added to cart successfully", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "404", description = "Client or Product not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping("/{clid}/{pid}/{amount}")
    public ResponseEntity<Cart> addProductToCart(@PathVariable Long clid, @PathVariable Long pid, @PathVariable Integer amount) {
        try {
            Cart cart = cartService.addProductToCart(clid, pid, amount);
            return ResponseEntity.status(HttpStatus.CREATED).body(cart);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Remove product from cart", description = "Removes a product from the client's cart")
    @ApiResponse(responseCode = "204", description = "Product removed from cart successfully", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "404", description = "Cart or Product not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @DeleteMapping("/{cartId}/products/{productId}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
        try {
            cartService.removeProductFromCart(cartId, productId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Get products in cart", description = "Retrieves all products in the client's cart that are not delivered")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "204", description = "No content")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping("/{clid}")
    public ResponseEntity<List<Cart>> getProductsInCart(@PathVariable Long clid) {
        try {
            List<Cart> carts = cartService.findByClientAndDelivered(clid);
            if (!carts.isEmpty()) {
                return ResponseEntity.ok(carts);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}