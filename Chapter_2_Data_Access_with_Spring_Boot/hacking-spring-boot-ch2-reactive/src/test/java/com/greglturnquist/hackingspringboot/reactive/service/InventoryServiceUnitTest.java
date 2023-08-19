package com.greglturnquist.hackingspringboot.reactive.service;


import com.greglturnquist.hackingspringboot.reactive.domain.Cart;
import com.greglturnquist.hackingspringboot.reactive.domain.CartItem;
import com.greglturnquist.hackingspringboot.reactive.domain.Item;
import com.greglturnquist.hackingspringboot.reactive.repositories.CartRepository;
import com.greglturnquist.hackingspringboot.reactive.repositories.ItemRepository;
import com.greglturnquist.hackingspringboot.reactive.services.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class InventoryServiceUnitTest {

    InventoryService inventoryService;

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private CartRepository cartRepository;

    @BeforeEach
    void Setup(){

        // Define test data
        Item sampleItem = new Item("item1", "TV tray", "Alf TV tray", 19.99);
        CartItem sampleCartItem = new CartItem(sampleItem);
        Cart sampleCart = new Cart("My Cart", Collections.singletonList(sampleCartItem));

        // Define mock interactions provided
        // by your collaborators ③
        when(cartRepository.findById(anyString())).thenReturn(Mono.empty());
        when(itemRepository.findById(anyString())).thenReturn(Mono.just(sampleItem));
        when(cartRepository.save(any(Cart.class))).thenReturn(Mono.just(sampleCart));

        inventoryService = new InventoryService(itemRepository, cartRepository);
        }




    @Test
    void addItemToEmptyCartShouldProduceOneCartItem() {

        inventoryService.addItemToCart("My Cart", "item1")
                .as(StepVerifier::create)
      .expectNextMatches(cart -> {
            assertThat(cart.getCartItems()).extracting(CartItem::getQuantity) //
                    .containsExactlyInAnyOrder(1);
            assertThat(cart.getCartItems()).extracting(CartItem::getItem) //
                    .containsExactly(new Item("item1", "TV tray", "Alf TV tray", 19.99));
            return true;
        }) //
                .verifyComplete();
    }

    }




