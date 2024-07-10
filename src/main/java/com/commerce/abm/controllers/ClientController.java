package com.commerce.abm.controllers;

import com.commerce.abm.entities.Client;
import com.commerce.abm.entities.Product;
import com.commerce.abm.services.ClientService;
import com.commerce.abm.services.InvoiceService;
import com.commerce.abm.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path= "api/v1/clientes")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ProductService productService;

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/register")
    public ResponseEntity<Client> registerClient(@RequestBody Client client) {
        try {
            return new ResponseEntity<>(clientService.saveClient(client), HttpStatus.CREATED);

        } catch (Exception e) {
            System.out.println(e);
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/me")
    public ResponseEntity<Client> updateClientProfile(@RequestBody Client updatedClient) {
        try {
            // Obtener el cliente existente por su ID
            Long clientId = updatedClient.getId();
            Optional<Client> optionalClient = clientService.getOneClient(clientId);

            if (optionalClient.isPresent()) {
                // Actualizar los campos del cliente existente
                Client existingClient = optionalClient.get();
                existingClient.setName(updatedClient.getName());
                existingClient.setLastname(updatedClient.getLastname());
                existingClient.setDocnumber(updatedClient.getDocnumber());

                // Guardar y devolver el cliente actualizado
                Client savedClient = clientService.saveClient(existingClient);
                return ResponseEntity.ok(savedClient);
            } else {
                // Cliente no encontrado
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Manejo de errores
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



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



    @PatchMapping ("/{id}")
    public  ResponseEntity<Client> updateClient (@PathVariable Long id, @RequestBody Client data ) {
        try {
            Optional<Client> optionalClient = clientService.getOneClient(id);
            if(optionalClient.isPresent()) {
                Client client = optionalClient.get();
                client.setName(data.getName());
                client.setLastname(data.getLastname());
                client.setDocnumber(data.getDocnumber());
                client.setProducts(client.getProducts());
                client.setInvoices(client.getInvoices());
                return ResponseEntity.ok(clientService.saveClient(client));
            }else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println(e);
        } return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping("/{clientId}/carts")
    public ResponseEntity<Client> addProductToCart(@PathVariable Long clientId, @RequestBody Product productToAdd) {
        try {
            Client updatedClient = clientService.addProductToCart(clientId, productToAdd);
            return ResponseEntity.ok(updatedClient);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




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
