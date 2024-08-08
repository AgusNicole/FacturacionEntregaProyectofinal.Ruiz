package com.commerce.abm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity @Table
@NoArgsConstructor @ToString @EqualsAndHashCode
public class Invoice {
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)@Getter @Setter private Long id;

    @Getter @Setter List<Cart> ordenes;
    @Getter @Setter private Date createdate;
    @Getter @Setter private Double total;

    @ManyToOne @JoinColumn (name= "client_id")
    @Getter @Setter private Client client;


    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Getter @Setter private List <Cart> carts;


}
