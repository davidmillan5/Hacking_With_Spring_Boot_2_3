package com.greglturnquist.hackingspringbootch6reactive.repositories;

import com.greglturnquist.hackingspringbootch6reactive.domain.Item;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ItemRepository extends ReactiveCrudRepository<Item, String> {
}
