package com.commerce.abm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity @Table
@NoArgsConstructor @ToString @EqualsAndHashCode
public class Cart {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Getter @Setter private Long id;

   @Getter @Setter private Double price ;
   @Getter @Setter private Integer amount;
   @Getter @Setter private boolean delivered;


    @ManyToOne
    @JoinColumn(name = "client", nullable = false)
    @JsonIgnore
    @Getter @Setter private Client client;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Getter @Setter
    @JsonIgnore
    private List<Product> products;


    @ManyToOne @JoinColumn (name= "invoice_id")
    @JsonIgnore
    @Getter @Setter private Invoice invoice;
}
