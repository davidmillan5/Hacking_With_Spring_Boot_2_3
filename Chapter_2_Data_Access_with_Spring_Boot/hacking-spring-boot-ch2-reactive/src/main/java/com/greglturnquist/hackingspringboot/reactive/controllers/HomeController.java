package com.greglturnquist.hackingspringboot.reactive.controllers;

import com.greglturnquist.hackingspringboot.reactive.domain.Cart;
import com.greglturnquist.hackingspringboot.reactive.repositories.CartRepository;
import com.greglturnquist.hackingspringboot.reactive.repositories.ItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
public class HomeController {

    private ItemRepository itemRepository;
    private CartRepository cartRepository;


    public HomeController(ItemRepository itemRepository, CartRepository cartRepository) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }

    @GetMapping
    Mono<Rendering> home(){
      return Mono.just(Rendering.view("home.html")
              .modelAttribute("items",
                      this.itemRepository.findAll())
              .modelAttribute("cart",
                      this.cartRepository.findById("My Cart")
                              .defaultIfEmpty(new Cart("My Cart")))
              .build());
    };


}
