package com.commerce.abm.repositories;

import com.commerce.abm.entities.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceDRepository extends JpaRepository<InvoiceDetail, Long> {
}
