package com.commerce.abm.services;

import com.commerce.abm.entities.InvoiceDetail;
import com.commerce.abm.entities.Product;
import com.commerce.abm.repositories.InvoiceDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceDService {

    @Autowired
    private InvoiceDRepository invoiceDRepository;


    public InvoiceDetail save(InvoiceDetail invoiceDetail) {
        return invoiceDRepository.save(invoiceDetail);
    }

    public List<InvoiceDetail> findAll() {
        return invoiceDRepository.findAll();
    }

    public Optional<InvoiceDetail> findById(Long id) {
        return invoiceDRepository.findById(id);
    }

    public void deleteById(Long id) {
        invoiceDRepository.deleteById(id);
    }



}
