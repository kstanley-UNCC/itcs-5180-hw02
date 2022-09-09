/*
 * Group 22 Homework 02
 * Drink.java
 * Ken Stanley & Stephanie Karp
 */
package com.example.group22_hw02;

import java.io.Serializable;

public class Drink implements Serializable {
    public final double drinkSize;
    public final double drinkAlcoholPercent;

    public Drink(double size, double alcohol) {
        this.drinkSize = size;
        this.drinkAlcoholPercent = alcohol;
    }
}
