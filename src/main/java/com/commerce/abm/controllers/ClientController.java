package com.commerce.abm.controllers;

import com.commerce.abm.entities.Client;
import com.commerce.abm.entities.Product;
import com.commerce.abm.services.ClientService;
import com.commerce.abm.services.InvoiceService;
import com.commerce.abm.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping(path= "api/v1/auth")
@Tag(name = "Routes of Client", description = "CRUD of clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ProductService productService;

    @Autowired
    private InvoiceService invoiceService;


    @Operation(summary = "Create Client", description = "Registers a new client in the system. The request body should contain the client's details.")
    @ApiResponse(responseCode = "201", description = "Client created successfully", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "Bad Request. The request body is invalid or missing required fields.")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping("/register")
    public ResponseEntity<Client> createClient(@Valid @RequestBody Client client) {
        try {
            return new ResponseEntity<>(clientService.saveClient(client), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




    @Operation(summary = "Update Client", description = "Update an existing client's details.")
    @ApiResponse(responseCode = "200", description = "Client updated successfully.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "Bad Request. The request body is invalid or missing required fields.")
    @ApiResponse(responseCode = "404", description = "Client not found. The ID provided does not match any existing client")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PutMapping("/me/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client data) {
        try {
            Optional<Client> optionalClient = clientService.getOneClient(id);
            if (optionalClient.isPresent()) {
                Client client = optionalClient.get();
                client.setName(data.getName());
                client.setLastname(data.getLastname());
                client.setDocnumber(data.getDocnumber());
                client.setInvoices(data.getInvoices());
                client.setCarts(data.getCarts());
                return ResponseEntity.ok(clientService.saveClient(client));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @Operation(summary = "Get All Clients", description = "Retrieves a list of all clients in the system.")
    @ApiResponse(responseCode = "200", description = "Clients retrieved successfully.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "204", description = "No Content. No clients found in the system.")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getAllClients() {
        try {
            List<Client> clients = clientService.findAllClients();
            if (!clients.isEmpty()) {
                return ResponseEntity.ok(clients);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
