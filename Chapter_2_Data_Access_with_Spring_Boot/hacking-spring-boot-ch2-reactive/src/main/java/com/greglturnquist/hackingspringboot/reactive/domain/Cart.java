package com.greglturnquist.hackingspringboot.reactive.domain;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    
    @Id
    private String id;
    
    private List<CartItem> cartItems;

    public Cart() {
    }

    public Cart(String id) {
        this(id,new ArrayList<>());
    }



    public Cart(String id, List<CartItem> cartItems) {
        this.id = id;
        this.cartItems = cartItems;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }


    @Override
    public String toString() {
        return "Cart{" +
                "id='" + id + '\'' +
                ", cartItems=" + cartItems +
                '}';
    }
}
