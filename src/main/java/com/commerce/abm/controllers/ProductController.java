package com.commerce.abm.controllers;

import com.commerce.abm.entities.Client;
import com.commerce.abm.entities.Product;
import com.commerce.abm.services.ClientService;
import com.commerce.abm.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
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


    @Operation(summary = "Create Product", description = "Registers a new product in the system. The request body should contain the product's details.")
    @ApiResponse(responseCode = "201", description = "Product created successfully.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "Bad Request. The request body is invalid or missing required fields.")
    @ApiResponse(responseCode = "500", description = "Internal server error. An unexpected error occurred while processing the request.")
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        try {
            return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Get All Products", description = "Retrieves a list of all products in the system.")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "204", description = "No Content. No products found in the system.")
    @ApiResponse(responseCode = "500", description = "Internal server error. An unexpected error occurred while processing the request.")
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

    @Operation(summary = "Get Product by ID", description = "Retrieves a product by its unique identifier. Returns the product details if found.")
    @ApiResponse(responseCode = "200", description = "Product retrieved successfully.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "Bad Request. The provided ID is invalid.")
    @ApiResponse(responseCode = "404", description = "Product not found. The ID provided does not match any existing product.")
    @ApiResponse(responseCode = "500", description = "Internal server error. An unexpected error occurred while processing the request.")
    @GetMapping("/{id}")
    public ResponseEntity<Product> readProductById(@PathVariable Long id) {
        try {
            Optional<Product> product = productService.getOneProduct(id);
            if (product.isPresent()) {
                return ResponseEntity.ok(product.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




    @Operation(summary = "Update Product", description = "Updates an existing product's details.")
    @ApiResponse(responseCode = "200", description = "Product updated successfully.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "Product not found. The ID provided does not match any existing product.")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product data) {
        try {
            return ResponseEntity.ok(productService.update(id, data));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}