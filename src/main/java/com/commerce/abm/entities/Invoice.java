package com.commerce.abm.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity @Table
@NoArgsConstructor @ToString @EqualsAndHashCode
public class Invoice {
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)@Getter @Setter private Long id;


    @Getter @Setter private LocalDate createdDate;
    @Getter @Setter private Double total;

    @ManyToOne @JoinColumn (name= "client_id")
    @Getter @Setter private Client client;


    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Getter @Setter private List <InvoiceDetail> invoiceDetails;


}
