package com.greglturnquist.hackingspringbootch6reactive.controllers;

import com.greglturnquist.hackingspringbootch6reactive.domain.Cart;
import com.greglturnquist.hackingspringbootch6reactive.domain.CartItem;
import com.greglturnquist.hackingspringbootch6reactive.domain.Item;
import com.greglturnquist.hackingspringbootch6reactive.repositories.CartRepository;
import com.greglturnquist.hackingspringbootch6reactive.repositories.ItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller // <1>
public class HomeController {

    private ItemRepository itemRepository;
    private CartRepository cartRepository;

    public HomeController(ItemRepository itemRepository, CartRepository cartRepository) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }
    // end::1[]

    // tag::2[]
    @GetMapping
    Mono<Rendering> home() {
        return Mono.just(Rendering.view("home.html")
                .modelAttribute("items", this.itemRepository.findAll()) // <3>
                .modelAttribute("cart", this.cartRepository.findById("My Cart") // <4>
                        .defaultIfEmpty(new Cart("My Cart")))
                .build());
    }
    // end::2[]

    // tag::3[]
    @GetMapping("/add/{id}")

    Mono<String> addToCart(@PathVariable String id) {
        return this.cartRepository.findById("My Cart")
                .defaultIfEmpty(new Cart("My Cart")) //
                .flatMap(cart -> cart.getCartItems().stream() // <4>
                        .filter(cartItem -> cartItem.getItem().getId().equals(id)) //
                        .findAny() //
                        .map(cartItem -> {
                            cartItem.increment();
                            return Mono.just(cart);
                        }).orElseGet(() -> { // <5>
                            return this.itemRepository.findById(id) //
                                    .map(item -> new CartItem(item)) //
                                    .map(cartItem -> {
                                        cart.getCartItems().add(cartItem);
                                        return cart;
                                    });
                        }))
                .flatMap(cart -> this.cartRepository.save(cart)) // <6>
                .then(Mono.just("redirect:/")); // <7>
    }
    // end::3[]

    @PostMapping
    Mono<String> createItem(@ModelAttribute Item newItem) {
        return this.itemRepository.save(newItem) //
                .then(Mono.just("redirect:/"));
    }

    @GetMapping("/delete/{id}")
    Mono<String> deleteItem(@PathVariable String id) {
        return this.itemRepository.deleteById(id) //
                .then(Mono.just("redirect:/"));
    }
}
