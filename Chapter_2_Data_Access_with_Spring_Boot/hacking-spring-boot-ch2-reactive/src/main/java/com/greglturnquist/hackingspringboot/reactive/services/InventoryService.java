package com.greglturnquist.hackingspringboot.reactive.services;

import com.greglturnquist.hackingspringboot.reactive.domain.Cart;
import com.greglturnquist.hackingspringboot.reactive.domain.CartItem;
import com.greglturnquist.hackingspringboot.reactive.domain.Item;
import com.greglturnquist.hackingspringboot.reactive.repositories.CartRepository;
import com.greglturnquist.hackingspringboot.reactive.repositories.ItemRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Service
public class InventoryService {

    private ItemRepository itemRepository;

    private CartRepository cartRepository;

    public InventoryService(ItemRepository repository, //
                            CartRepository cartRepository) {
        this.itemRepository = repository;
        this.cartRepository = cartRepository;
    }

    public Mono<Cart> getCart(String cartId) {
        return this.cartRepository.findById(cartId);
    }

    public Flux<Item> getInventory() {
        return this.itemRepository.findAll();
    }

    Mono<Item> saveItem(Item newItem) {
        return this.itemRepository.save(newItem);
    }

    Mono<Void> deleteItem(String id) {
        return this.itemRepository.deleteById(id);
    }

    // tag::logging[]
    public Mono<Cart> addItemToCart(String cartId, String itemId) {
        return this.cartRepository.findById(cartId) //
                .log("foundCart") //
                .defaultIfEmpty(new Cart(cartId)) //
                .log("emptyCart") //
                .flatMap(cart -> cart.getCartItems().stream() //
                        .filter(cartItem -> cartItem.getItem() //
                                .getId().equals(itemId))
                        .findAny() //
                        .map(cartItem -> {
                            cartItem.increment();
                            return Mono.just(cart).log("newCartItem");
                        }) //
                        .orElseGet(() -> {
                            return this.itemRepository.findById(itemId) //
                                    .log("fetchedItem") //
                                    .map(item -> new CartItem(item)) //
                                    .log("cartItem") //
                                    .map(cartItem -> {
                                        cart.getCartItems().add(cartItem);
                                        return cart;
                                    }).log("addedCartItem");
                        }))
                .log("cartWithAnotherItem") //
                .flatMap(cart -> this.cartRepository.save(cart)) //
                .log("savedCart");
    }
    // end::logging[]

    Mono<Cart> removeOneFromCart(String cartId, String itemId) {
        return this.cartRepository.findById(cartId) //
                .defaultIfEmpty(new Cart(cartId)) //
                .flatMap(cart -> cart.getCartItems().stream() //
                        .filter(cartItem -> cartItem.getItem() //
                                .getId().equals(itemId))
                        .findAny() //
                        .map(cartItem -> {
                            cartItem.decrement();
                            return Mono.just(cart);
                        }) //
                        .orElse(Mono.empty())) //
                .map(cart -> new Cart(cart.getId(), cart.getCartItems().stream() //
                        .filter(cartItem -> cartItem.getQuantity() > 0) //
                        .collect(Collectors.toList()))) //
                .flatMap(cart -> this.cartRepository.save(cart));
    }
}
