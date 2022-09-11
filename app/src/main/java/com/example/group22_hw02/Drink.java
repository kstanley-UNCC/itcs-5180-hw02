/*
 * Group 22 Homework 02
 * Drink.java
 * Ken Stanley & Stephanie Karp
 */
package com.example.group22_hw02;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Drink implements Serializable {
    public final double drinkSize;
    public final double drinkAlcoholPercent;
    public final Date dateTime;

    public Drink(double size, double alcohol) {
        this.drinkSize = size;
        this.drinkAlcoholPercent = alcohol;
        this.dateTime = Calendar.getInstance().getTime();
    }

    public double getLiquidOunces() {
        return (drinkAlcoholPercent * drinkSize) / 100;
    }
}
