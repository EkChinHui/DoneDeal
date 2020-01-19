package com.nus.donedeal;

/**
 * this code is unused, originally intended to implement split
 * manually function
 */

public class OneString {
    private String name;
    private Float price;

    public OneString(String name, Float price) {
        this.name = name;
        this.price = price;
    }

    public Float getPrice() {
        return price;
    }
    public String getName() { return name; }
    public void setPrice(float new_price) {
        price = new_price;
    }
}