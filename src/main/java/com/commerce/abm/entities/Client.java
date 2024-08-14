package com.commerce.abm.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import lombok.*;

import java.util.List;

@Entity
@Table(name = "clients")
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Getter @Setter private String name;

    @NotBlank(message = "Lastname is mandatory")
    @Getter @Setter private String lastname;

    @NotNull
    @Getter @Setter  private Integer docnumber;


    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference  // Permite la serialización desde Cart hacia Client
    @Getter @Setter  private List<Invoice> invoices;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @Getter @Setter
    private List<Cart> carts ;

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", docnumber=" + docnumber +
                '}';
    }
}
