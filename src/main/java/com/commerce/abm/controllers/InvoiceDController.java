package com.commerce.abm.controllers;

import com.commerce.abm.entities.Invoice;
import com.commerce.abm.entities.InvoiceDetail;
import com.commerce.abm.entities.Product;
import com.commerce.abm.services.InvoiceDService;
import com.commerce.abm.services.InvoiceService;
import com.commerce.abm.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/invoice-details")
public class InvoiceDController {
    @Autowired
    private InvoiceDService invoiceDetailService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private ProductService productService;



}
