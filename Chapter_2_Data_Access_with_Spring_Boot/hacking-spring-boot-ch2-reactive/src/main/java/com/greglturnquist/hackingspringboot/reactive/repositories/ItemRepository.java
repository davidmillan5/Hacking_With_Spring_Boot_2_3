package com.greglturnquist.hackingspringboot.reactive.repositories;

import com.greglturnquist.hackingspringboot.reactive.domain.Item;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ItemRepository extends ReactiveCrudRepository<Item,String> {
}
