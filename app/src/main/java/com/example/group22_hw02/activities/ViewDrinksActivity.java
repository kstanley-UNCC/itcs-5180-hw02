/*
 * Group 22 Homework 02
 * ViewDrinksActivity.java
 * Ken Stanley & Stephanie Karp
 */
package com.example.group22_hw02.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.group22_hw02.Drink;
import com.example.group22_hw02.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ViewDrinksActivity extends AppCompatActivity {

    TextView viewAlcoholPercent;
    TextView viewCurrentDrinkNumber;
    TextView viewDateAdded;
    TextView viewDrinkSize;
    ImageButton buttonTrash;
    Button buttonClose;
    ImageButton buttonPrevious;
    ImageButton buttonNext;

    int currentDrinkNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdrinks);

        setTitle(R.string.app_activity_view_drinks);

        viewAlcoholPercent = findViewById(R.id.viewAlcoholPercent);
        viewCurrentDrinkNumber = findViewById(R.id.viewCurrentDrinkNumber);
        viewDateAdded = findViewById(R.id.viewDateAdded);
        viewDrinkSize = findViewById(R.id.viewDrinkSize);
        buttonClose = findViewById(R.id.buttonClose);
        buttonPrevious = findViewById(R.id.buttonPrevious);
        buttonNext = findViewById(R.id.buttonNext);

        updateView(currentDrinkNumber);

        ArrayList<Drink> drinkArrayList = getIntent().getParcelableArrayListExtra(getString(R.string.intent_drink_list));

        // Trash button deletes current drink from ArrayList
        findViewById(R.id.buttonTrash).setOnClickListener(v -> {
            drinkArrayList.remove(currentDrinkNumber);

            if (drinkArrayList.isEmpty()) {
                buttonClose.performClick();
                return;
            }
            buttonPrevious.performClick();
        });

        // Close button returns to MainActivity with updated arrayList
        buttonClose.setOnClickListener(v -> {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.putExtra(getString(R.string.intent_drink_list), drinkArrayList);
            setResult(MainActivity.ACTIVITY_DRINK_VIEW, intent);

            finish();
        });

        // Previous button shows the drink before the current drink
        findViewById(R.id.buttonPrevious).setOnClickListener(v -> {
            currentDrinkNumber--;
            if(currentDrinkNumber < 0) {
                currentDrinkNumber = drinkArrayList.size() - 1;
            }
            updateView(currentDrinkNumber);
        });

        // Next button shows the drink after the current drink
        findViewById(R.id.buttonNext).setOnClickListener((v -> {
            currentDrinkNumber++;
            if (currentDrinkNumber >= drinkArrayList.size()) {
                currentDrinkNumber = 0;
            }
            updateView(currentDrinkNumber);
        }));
    }

    private void updateView (int drinkNumber) {
        ArrayList<Drink> drinkArrayList = getIntent().getParcelableArrayListExtra(getString(R.string.intent_drink_list));

        Drink currentDrink = drinkArrayList.get(drinkNumber);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US);

        Date drinkDate = Calendar.getInstance().getTime();
        drinkDate.setTime(currentDrink.dateTime);

        viewCurrentDrinkNumber.setText(getString(R.string.view_current_drink_number, drinkNumber + 1, drinkArrayList.size()));
        viewAlcoholPercent.setText(getString(R.string.view_alcohol_percent, currentDrink.drinkAlcoholPercent));
        viewDrinkSize.setText(getString(R.string.view_drink_size, currentDrink.drinkSize));
        viewDateAdded.setText(getString(R.string.view_date_added, dateFormat.format(drinkDate)));
    }
}
