/*
 * Group 22 Homework 02
 * ViewDrinksActivity.java
 * Ken Stanley & Stephanie Karp
 */
package com.example.group22_hw02.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.group22_hw02.Drink;
import com.example.group22_hw02.R;

import java.util.ArrayList;
import java.util.Date;

public class ViewDrinksActivity extends AppCompatActivity {

    TextView viewAlcoholPercent;
    TextView viewCurrentDrinkNumber;
    TextView viewDateAdded;
    TextView viewDrinkSize;
    Button buttonTrash;
    Button buttonClose;
    Button buttonPrevious;
    Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.app_activity_view_drinks);

        viewAlcoholPercent = findViewById(R.id.viewAlcoholPercent);
        viewCurrentDrinkNumber = findViewById(R.id.viewCurrentDrinkNumber);
        viewDateAdded = findViewById(R.id.viewDateAdded);
        viewDrinkSize = findViewById(R.id.viewDrinkSize);

        ArrayList<Drink> drinkArrayList = (ArrayList<Drink>) getIntent().getSerializableExtra("Drinks");
        double currentDrinkSize = drinkArrayList.get(0).getDrinkSize();
        double currentAlcoholPercent = drinkArrayList.get(0).getAlcoholPercent();
        double totalDrinks = drinkArrayList.size();
        int currentDrinkNumber = (drinkArrayList.indexOf(0)) + 1;
        //Date dateDrinkAdded;

        Drink currentDrink = new Drink(currentDrinkSize, currentAlcoholPercent);

        viewCurrentDrinkNumber.setText("Drink " + currentDrinkNumber + " out of " + totalDrinks);
        viewAlcoholPercent.setText((currentDrink.drinkAlcoholPercent) + "Alcohol");
        viewDrinkSize.setText(currentDrink.drinkSize + " oz");
        //viewDateAdded.setText();

        // Trash button deletes current drink from ArrayList
        findViewById(R.id.buttonTrash).setOnClickListener(v -> {
            drinkArrayList.remove(drinkArrayList.indexOf(this));
            if (drinkArrayList.isEmpty()) {
                finish();
            } else {
                viewCurrentDrinkNumber.setText("Drink " + currentDrinkNumber + " out of " + totalDrinks);
            }
        });

        // Close button returns to MainActivity with updated arrayList
        findViewById(R.id.buttonClose).setOnClickListener(v -> {
            // Send back updated ArrayList

            // Should this be an if statement so it only updates if there were changes?
            Intent intent = new Intent(ViewDrinksActivity.this, MainActivity.class);
            intent.putExtra("Updated Drinks", drinkArrayList);
            setResult(MainActivity.ACTIVITY_DRINK_VIEW, intent);
            finish();
        });

        // Previous button shows the drink before the current drink
        findViewById(R.id.buttonPrevious).setOnClickListener(v -> {

        });

        // Next button shows the drink after the current drink
        findViewById(R.id.buttonNext).setOnClickListener((v -> {

        }));
    }
}
