package com.nus.donedeal;

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

}