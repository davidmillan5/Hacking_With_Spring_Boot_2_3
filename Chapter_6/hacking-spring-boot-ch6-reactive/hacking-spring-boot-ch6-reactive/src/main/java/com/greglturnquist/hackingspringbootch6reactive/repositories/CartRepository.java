package com.greglturnquist.hackingspringbootch6reactive.repositories;

import com.greglturnquist.hackingspringbootch6reactive.domain.Cart;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CartRepository extends ReactiveCrudRepository<Cart, String> {
}
