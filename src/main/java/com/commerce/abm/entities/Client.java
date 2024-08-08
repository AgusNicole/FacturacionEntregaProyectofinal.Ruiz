package com.commerce.abm.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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

    @NotNull(message = "Name cannot be null")
    @Getter @Setter private String name;

    @NotNull(message = "lastname cannot be null")
    @Getter @Setter private String lastname;

    @NotNull(message = "docnumber cannot be null")
    @Getter @Setter  private Integer docnumber;


    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Getter @Setter  private List<Invoice> invoices;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Getter @Setter
    private List<Cart> carts;

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
