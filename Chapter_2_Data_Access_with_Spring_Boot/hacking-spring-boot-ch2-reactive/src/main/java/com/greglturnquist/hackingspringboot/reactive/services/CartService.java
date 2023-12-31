package com.greglturnquist.hackingspringboot.reactive.services;

import com.greglturnquist.hackingspringboot.reactive.domain.Cart;
import com.greglturnquist.hackingspringboot.reactive.domain.CartItem;
import com.greglturnquist.hackingspringboot.reactive.repositories.CartRepository;
import com.greglturnquist.hackingspringboot.reactive.repositories.ItemRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CartService {

    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    public CartService(ItemRepository itemRepository, CartRepository cartRepository) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }


    public Mono<Cart> addToCart(String cartId, String id) { // <3>
        return this.cartRepository.findById(cartId) //
                .defaultIfEmpty(new Cart(cartId)) //
                .flatMap(cart -> cart.getCartItems().stream() //
                        .filter(cartItem -> cartItem.getItem() //
                                .getId().equals(id)) //
                        .findAny() //
                        .map(cartItem -> {
                            cartItem.increment();
                            return Mono.just(cart);
                        }) //
                        .orElseGet(() -> //
                                this.itemRepository.findById(id) //
                                        .map(CartItem::new) // <4>
                                        .doOnNext(cartItem -> //
                                                cart.getCartItems().add((CartItem) cartItem)) //
                                        .map(cartItem -> cart)))
                .flatMap(this.cartRepository::save); // <5>
    }

}
