package com.greglturnquist.hackingspringboot.reactive.domain;

public class CartItem {

    private Item item;
    private int quantity;

    public CartItem() {
    }

    public CartItem(Item item) {
        this.item = item;
        this.quantity = 1;
    }

    public void increment() {
        this.quantity++;
    }

    public void decrement() {
        this.quantity--;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "item=" + item +
                ", quantity=" + quantity +
                '}';
    }




}
