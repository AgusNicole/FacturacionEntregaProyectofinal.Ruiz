package com.commerce.abm.repositories;

import com.commerce.abm.entities.Client;
import com.commerce.abm.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByClient(Client client);
}
