package com.commerce.abm.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity @Table (name="products")
@NoArgsConstructor @ToString @EqualsAndHashCode
public class Product {
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY) @Getter @Setter private Long id;

    @Getter @Setter private String description;
    @Getter @Setter private String code;
    @Getter @Setter private Integer stock;
    @Getter @Setter private Double price;

    @ManyToOne @JoinColumn (name= "client_id")
    @Getter @Setter private Client client;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Getter @Setter private List <InvoiceDetail> invoiceDetails;

}
