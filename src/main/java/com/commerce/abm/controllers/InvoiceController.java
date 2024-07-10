package com.commerce.abm.controllers;

import com.commerce.abm.entities.Client;
import com.commerce.abm.entities.Invoice;
import com.commerce.abm.entities.InvoiceDetail;
import com.commerce.abm.services.ClientService;
import com.commerce.abm.services.InvoiceDService;
import com.commerce.abm.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private InvoiceDService invoiceDetailService;

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(
            @RequestParam Long clientId,
            @RequestBody List<InvoiceDetail> invoiceDetails) {
        try {
            // Obtener el cliente por su ID
            Optional<Client> optionalClient = clientService.getOneClient(clientId);
            if (!optionalClient.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            // Crear la factura
            Invoice invoice = new Invoice();
            invoice.setClient(optionalClient.get());
            invoice.setCreatedDate(LocalDate.now());

            double total = invoiceDetails.stream()
                    .mapToDouble(detail -> detail.getOwner().getPrice() * detail.getQuantity())
                    .sum();
            invoice.setTotal(total);

            invoice.setInvoiceDetails(invoiceDetails);

            // Guardar la factura en la base de datos
            invoice = invoiceService.saveInvoice(invoice);

            return new ResponseEntity<>(invoice, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<List<Invoice>> getInvoicesByClient(@PathVariable Long clientId) {
        try {
            List<Invoice> invoices = invoiceService.getInvoicesByClientId(clientId);
            if (!invoices.isEmpty()) {
                return ResponseEntity.ok(invoices);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
