package com.commerce.abm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity @Table
@NoArgsConstructor @ToString @EqualsAndHashCode
public class Invoice {
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)@Getter @Setter private Long id;

    @Getter @Setter private Date createdate;
    @Getter @Setter private Double total;


    @ManyToOne @JoinColumn (name= "client_id")
    @JsonManagedReference
    @Getter @Setter private Client client;





}
