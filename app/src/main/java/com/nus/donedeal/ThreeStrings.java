package com.nus.donedeal;

public class ThreeStrings {
    private String left;
    private String right;
    private Float centre;

    public ThreeStrings(String left, String right, Float centre) {
        this.left = left;
        this.right = right;
        this.centre = centre;
    }

    public String getLeft() {
        return left;
    }
    public String getRight() {
        return right;
    }
    public Float getCentre() {
        return centre;
    }

}