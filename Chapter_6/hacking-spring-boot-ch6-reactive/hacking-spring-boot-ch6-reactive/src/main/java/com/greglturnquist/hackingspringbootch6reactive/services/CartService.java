package com.greglturnquist.hackingspringbootch6reactive.services;

import com.greglturnquist.hackingspringbootch6reactive.domain.Cart;
import com.greglturnquist.hackingspringbootch6reactive.domain.CartItem;
import com.greglturnquist.hackingspringbootch6reactive.repositories.CartRepository;
import com.greglturnquist.hackingspringbootch6reactive.repositories.ItemRepository;
import reactor.core.publisher.Mono;

public class CartService {
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    CartService(ItemRepository itemRepository, CartRepository cartRepository) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }

    Mono<Cart> addToCart(String cartId, String id) { // <3>
        return this.cartRepository.findById(cartId) //
                .defaultIfEmpty(new Cart(cartId)) //
                .flatMap(cart -> cart.getCartItems().stream() //
                        .filter(cartItem -> cartItem.getItem().getId().equals(id)) //
                        .findAny() //
                        .map(cartItem -> {
                            cartItem.increment();
                            return Mono.just(cart);
                        }).orElseGet(() -> {
                            return this.itemRepository.findById(id) //
                                    .map(CartItem::new) // <4>
                                    .doOnNext(cartItem -> cart.getCartItems().add(cartItem)) //
                                    .map(cartItem -> cart);
                        }))
                .flatMap(this.cartRepository::save); // <5>
    }
}
