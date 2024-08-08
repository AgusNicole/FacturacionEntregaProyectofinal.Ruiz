package com.commerce.abm.services;

import com.commerce.abm.entities.*;
import com.commerce.abm.repositories.CartRepository;
import com.commerce.abm.repositories.ClientsRepository;
import com.commerce.abm.repositories.InvoiceRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ClientsRepository clientsRepository;



public Invoice generateInvoice(Long clientId){
    Optional<Client> client = clientsRepository.findById(clientId);
    if(client.isPresent()){
        List<Cart> carts = cartRepository.findByClientAndDelivered(clientId, false);
        if(carts.isEmpty()){
            throw new RuntimeException("Not found products on cart");
        }else {
            Client foundClient = client.get();
            Invoice invoice = new Invoice();
            invoice.setClient(foundClient);
            invoice.setCreatedate(new Date());
            double total = 00;
            for (Cart cart : carts){
                total += cart.getAmount() * cart.getPrice();
                cart.setDelivered(true);
            }
            invoice.setTotal(total);
            return invoiceRepository.save(invoice);
        }

    }else {
        throw new RuntimeException("Client not found");
    }
}



public  Invoice getInvoiceByClientId (Long clientId) {
    Optional<Client> client = clientsRepository.findById(clientId);
    if(client.isPresent()) {
     List<Invoice> invoices = client.get().getInvoices();
        if(invoices.isEmpty()){
            throw new RuntimeException("No invoices found for the client");
        }
        Invoice lastinvoice =invoices.get(invoices.size() -1);
        return lastinvoice;
    } else {
        throw  new RuntimeException("Client not found");
    }
}

}


