package com.commerce.abm.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Table(name = "products")
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;

    @Getter @Setter private String title;
    @Getter @Setter private String description;
    @Getter @Setter private Integer stock;
    @Getter @Setter private Double price;

   @OneToMany (mappedBy= "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   @JsonManagedReference
    private List<Cart> cart;
}






