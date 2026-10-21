package com.mbalenhle.smartpantrymanager;

public class RecipeIngredient {
    private String name;
    private double requiredQty;

    public RecipeIngredient(String name, double requiredQty) {
        this.name = name;
        this.requiredQty = requiredQty;
    }

    public String getName() { return name; }
    public double getRequiredQty() { return requiredQty; }
}
