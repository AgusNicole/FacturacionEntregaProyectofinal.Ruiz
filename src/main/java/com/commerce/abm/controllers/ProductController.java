package com.commerce.abm.controllers;

import com.commerce.abm.entities.Product;
import com.commerce.abm.services.ClientService;
import com.commerce.abm.services.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping(path = "api/v1/products")
@Tag( name ="Ruts of Products", description = "CRUD of products ")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        try {
            return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Product>> readAllProducts() {
        try {
            List<Product> allProducts = productService.findAll();
            if (!allProducts.isEmpty()) {
                return ResponseEntity.ok(allProducts);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("api/v1/products/:pid")
    public ResponseEntity<Object> readProductById(@PathVariable Long id) {
        try {
            Optional<Product> product = productService.findByID(id);
            if (product.isPresent()) {
                return ResponseEntity.ok(product.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        try {
            Product updated = productService.update(id, updatedProduct);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }





}