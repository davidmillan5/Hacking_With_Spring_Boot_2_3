package com.greglturnquist.hackingspringboot.reactive.controllers;

import com.greglturnquist.hackingspringboot.reactive.domain.Cart;
import com.greglturnquist.hackingspringboot.reactive.repositories.CartRepository;
import com.greglturnquist.hackingspringboot.reactive.repositories.ItemRepository;
import com.greglturnquist.hackingspringboot.reactive.services.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
public class HomeController {

    private ItemRepository itemRepository;
    private CartRepository cartRepository;
    private final CartService cartService;


    public HomeController(ItemRepository itemRepository, CartRepository cartRepository, CartService cartService) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
        this.cartService = cartService;
    }

    @GetMapping
    Mono<Rendering> home(){
      return Mono.just(Rendering.view("home.html")
              .modelAttribute("items",
                      this.itemRepository.findAll()
                              .doOnNext(System.out::println))
              .modelAttribute("cart",
                      this.cartRepository.findById("My Cart")
                              .defaultIfEmpty(new Cart("My Cart")))
              .build());
    };

    @PostMapping("/add/{id}") // <1>

    Mono<String> addToCart(@PathVariable String id) {
        return this.cartService.addToCart("My Cart", id) //
                .thenReturn("redirect:/");

    }
}
