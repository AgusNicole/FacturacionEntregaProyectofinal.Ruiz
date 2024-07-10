package com.commerce.abm.services;

import com.commerce.abm.entities.*;
import com.commerce.abm.repositories.InvoiceDRepository;
import com.commerce.abm.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceDRepository invoiceDetailRepository;

    public Invoice createInvoice(Client client, List<InvoiceDetail> invoiceDetails) {
        Invoice invoice = new Invoice();
        invoice.setClient(client);
        invoice.setCreatedDate(LocalDate.now());

        double total = invoiceDetails.stream()
                .mapToDouble(detail -> detail.getOwner().getPrice() * detail.getQuantity())
                .sum();
        invoice.setTotal(total);

        invoice.setInvoiceDetails(invoiceDetails);

        return invoiceRepository.save(invoice);
    }


    public Invoice saveInvoice(Invoice invoice) {
        try {
            return invoiceRepository.save(invoice);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la factura: " + e.getMessage());
        }
    }



    public List<Invoice> getInvoicesByClientId(Long clientId) {
        return invoiceRepository.findByClientId(clientId);
    }

    public Optional<Invoice> getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }

    private double calculateTotal(List<InvoiceDetail> invoiceDetails) {
        return invoiceDetails.stream()
                .mapToDouble(detail -> detail.getOwner().getPrice() * detail.getQuantity())
                .sum();
    }
}


