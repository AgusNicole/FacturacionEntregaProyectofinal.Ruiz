package com.commerce.abm.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity @Table (name="clients")
@NoArgsConstructor @ToString @EqualsAndHashCode
public class Client {
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY) @Getter @Setter private Long id;

    @Getter @Setter private String name;
    @Getter @Setter private String lastname;
    @Getter @Setter private Integer docnumber;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Getter @Setter private List<Product> products;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Getter @Setter private List<Invoice> invoices;

}
