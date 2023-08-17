package com.greglturnquist.hackingspringboot.reactive.repositories;

import com.greglturnquist.hackingspringboot.reactive.domain.Item;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;

public interface ItemByExampleRepository  extends ReactiveQueryByExampleExecutor<Item> {
}
