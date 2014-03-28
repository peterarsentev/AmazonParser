package com.amazon.parser.model;

/**
 * This is goods model
 */
public class Goods {
    private final String name;
    private final String price;

    public Goods(String name, String price) {
        this.name = name;
        this.price = price;
    }

    // It should have the getters methods for json mapping
    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}
