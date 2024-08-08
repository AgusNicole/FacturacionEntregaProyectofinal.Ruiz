package com.commerce.abm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne
    @JoinColumn(name = "cart") // Asegúrate de que la columna `cart_id` exista en la tabla `products`
    @JsonIgnore
    @Getter @Setter private Cart cart;
}






