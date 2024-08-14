package com.commerce.abm.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity @Table
@NoArgsConstructor @ToString @EqualsAndHashCode
public class Cart {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Getter @Setter private Long id;

   @Getter @Setter private Double price ;
   @Getter @Setter private Integer amount;
   @Getter @Setter private boolean delivered;


    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @JsonBackReference// Evita la serialización cíclica hacia Client
    @Getter @Setter private Client client;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
   @Getter @Setter private Product product;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    @JsonBackReference
    @Getter @Setter private Invoice invoice;
}
