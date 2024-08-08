package com.commerce.abm.repositories;

import com.commerce.abm.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
   List<Cart> findByClientAndDelivered(Long clientId, Boolean delivered);
}
