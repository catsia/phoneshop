package com.es.core.order;

public class OrderNotFound extends Exception {
    public OrderNotFound(String message) {
        super(message);
    }

    public OrderNotFound() {
    }
}
