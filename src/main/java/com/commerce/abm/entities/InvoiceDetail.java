package com.commerce.abm.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table
@NoArgsConstructor @ToString @EqualsAndHashCode
public class InvoiceDetail {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Getter @Setter private Long id;

   @Getter @Setter private Integer quantity;


    @ManyToOne @JoinColumn (name= "product_id")
    @Getter @Setter  private Product owner;

    @ManyToOne @JoinColumn (name= "invoice_id")
    @Getter @Setter private Invoice invoice;
}
