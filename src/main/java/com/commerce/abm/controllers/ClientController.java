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
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;
import java.util.Optional;
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping(path= "api/v1/clientes")
@Tag(name = "Routes of Client", description = "CRUD of clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ProductService productService;

    @Autowired
    private InvoiceService invoiceService;


    @Operation(summary = "create Client", description = "Registers a new client in the system. The request body should contain the client's details.")
    @ApiResponse(responseCode = "201", description = "Client created successfully", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "204", description = "No content")
    @ApiResponse( responseCode = "400", description = "Bad Request. The request body is invalid or missing required fields." )
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping("/register")
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        try {
            return new ResponseEntity<>(clientService.saveClient(client), HttpStatus.CREATED);

        } catch (Exception e) {
            System.out.println(e);
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @Operation(summary = "Get Client by ID ", description = "Retrieves a client by their unique identifier. Returns the client details if found.")
    @ApiResponse(responseCode = "200", description = "Client retrieved successfully.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "404", description = "Client not found. The ID provided does not match any existing client.")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getClientById(@PathVariable Long id){
        try {
            Optional<Client> client = clientService.getOneClient(id);
            if(client.isPresent()) {
                return ResponseEntity.ok(client.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e){
            System.out.println(e);
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "update client", description = "actualice client ")
    @ApiResponse(responseCode = "200", description = "Client updated successfully.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "404", description = "Client not found. The ID provided does not match any existing client")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PatchMapping ("/{me}")
    public  ResponseEntity<Client> updateClient (@PathVariable Long id, @RequestBody Client data ) {
        try {
            Optional<Client> optionalClient = clientService.getOneClient(id);
            if(optionalClient.isPresent()) {
                Client client = optionalClient.get();
                client.setName(data.getName());
                client.setLastname(data.getLastname());
                client.setDocnumber(data.getDocnumber());
                client.setInvoices(client.getInvoices());
                return ResponseEntity.ok(clientService.saveClient(client));
            }else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println(e);
        } return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    @Operation(summary = "Delete Client", description = "Deletes a client by their unique identifier. If the client is successfully deleted, a confirmation response is returned.")
    @ApiResponse(responseCode = "204", description = "Client deleted successfully.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "404", description = "Client not found. The ID provided does not match any existing client")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteClient (@PathVariable Long id) {
        try {
            Optional<Client> optionalClient = clientService.getOneClient(id);
            if (optionalClient.isPresent()) {
                clientService.deleteOneClient(id);
                return ResponseEntity.ok().build();
            }else {
                return ResponseEntity.notFound().build(); }

        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }







}
