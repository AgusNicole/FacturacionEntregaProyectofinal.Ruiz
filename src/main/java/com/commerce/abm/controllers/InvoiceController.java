package com.commerce.abm.controllers;

import com.commerce.abm.entities.Client;
import com.commerce.abm.entities.Invoice;
import com.commerce.abm.entities.Cart;
import com.commerce.abm.services.ClientService;
import com.commerce.abm.services.CartService;
import com.commerce.abm.services.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/v1/invoices")
@Tag(name = "Routes of Invoice", description = "CRUD of invoices")
public class InvoiceController {


    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private ClientService clientService;

    @PostMapping("/{clid}")
    public Invoice generateInvoice(@PathVariable("clid") Long clientId) {
        return invoiceService.generateInvoice(clientId);
    }

    @Operation(summary = "Create Invoice", description = "Generates a new invoice for a client using their unique identifier.")
    @ApiResponse(responseCode = "201", description = "Invoice created successfully.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "Bad Request. The ID is invalid or missing.")
    @ApiResponse(responseCode = "404", description = "Client not found. The ID provided does not match any existing.")
    @ApiResponse(responseCode = "500", description = "Internal server error. An unexpected error occurred while processing the request.")
    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestParam Long clientId) {
        try {
            Invoice invoice = invoiceService.generateInvoice(clientId);
            return ResponseEntity.status(HttpStatus.CREATED).body(invoice);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @Operation(summary = "Get Invoice by Client ID", description = "Retrieves an invoice associated with a client by their unique identifier.")
    @ApiResponse(responseCode = "200", description = "Invoice retrieved successfully.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "Bad Request. The  ID is invalid.")
    @ApiResponse(responseCode = "404", description = "Invoice not found..")
    @ApiResponse(responseCode = "500", description = "Internal server error. An unexpected error occurred while processing the request.")
    @GetMapping("/{clid}")
    public ResponseEntity<Invoice> getInvoiceByClientId(@PathVariable Long clientId) {
        try {
            Invoice invoice = invoiceService.getInvoiceByClientId(clientId);
            return ResponseEntity.ok(invoice);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
