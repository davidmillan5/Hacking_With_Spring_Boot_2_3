package com.greglturnquist.hackingspringboot.reactive.repositories;

import com.greglturnquist.hackingspringboot.reactive.domain.Item;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ItemRepository extends ReactiveCrudRepository<Item,String> {

    Flux<Item> findByNameContaining(String partialName);
}
