/*
 * Group 22 Homework 02
 * AddDrinkActivity.java
 * Ken Stanley & Stephanie Karp
 */
package com.example.group22_hw02.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.group22_hw02.Drink;
import com.example.group22_hw02.R;

public class AddDrinkActivity extends AppCompatActivity {
    protected final int DRINK_SMALL = 1;
    protected final int DRINK_MEDIUM = 5;
    protected final int DRINK_LARGE = 12;
    protected final int ALCOHOL_PERCENT_MAX = 30;

    RadioGroup drinkSizeGroup;
    SeekBar alcoholPercentBar;
    TextView alcoholPercent;
    Button buttonDrinkAdd;
    Button buttonCancel;

    double drinkOunces;
    double drinkPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddrink);

        setTitle(R.string.app_activity_add_drink);

        alcoholPercentBar = findViewById(R.id.alcoholPercentBar);
        drinkSizeGroup = findViewById(R.id.drinkSizeGroup);
        buttonDrinkAdd = findViewById(R.id.buttonDrinkAdd);
        buttonCancel = findViewById(R.id.buttonCancel);
        alcoholPercent = findViewById(R.id.alcoholPercent);


        drinkSizeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (drinkSizeGroup.getCheckedRadioButtonId()) {
                case R.id.drinkSizeOption1:
                    drinkOunces = DRINK_SMALL;
                    break;
                case R.id.drinkSizeOption2:
                    drinkOunces = DRINK_MEDIUM;
                    break;
                case R.id.drinkSizeOption3:
                    drinkOunces = DRINK_LARGE;
                    break;
            }
        });

        alcoholPercentBar.setMax(ALCOHOL_PERCENT_MAX);

        alcoholPercentBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                alcoholPercent.setText(getString(R.string.alcohol_percent_view, progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Cancel button returns to MainActivity
        findViewById(R.id.buttonCancel).setOnClickListener(v -> finish());

        // Creates a drink and sends back to main activity
        findViewById(R.id.buttonDrinkSet).setOnClickListener(v -> {
            if (!validateDrinkSize()) {
                Toast.makeText(getApplicationContext(), getString(R.string.validation_drink_size), Toast.LENGTH_SHORT).show();
                return;
            }

            drinkPercent = alcoholPercentBar.getProgress();

            if (!validateAlcoholPercent()) {
                Toast.makeText(getApplicationContext(), getString(R.string.validation_alcohol_percent), Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(AddDrinkActivity.this, MainActivity.class);
            intent.putExtra(getString(R.string.intent_drink), new Drink(drinkOunces, drinkPercent));
            setResult(MainActivity.ACTIVITY_DRINK_ADD, intent);
            finish();
        });
    }

    /**
     * Validate the following rules:
     *
     * 1. The Drink Size is 1, 5, or 12.
     *
     * @return boolean
     */
    @SuppressLint("NonConstantResourceId")
    private boolean validateDrinkSize() {
        switch (drinkSizeGroup.getCheckedRadioButtonId()) {
            case R.id.drinkSizeOption1:
            case R.id.drinkSizeOption2:
            case R.id.drinkSizeOption3:
                return true;
            default:
                return false;
        }
    }

    /**
     * Validate the following rules:
     *
     * 1. The Alcohol % has been chosen
     *
     * @return boolean
     */
    private boolean validateAlcoholPercent() {
        if (drinkPercent > 0) {
            return true;
        } else {
            return false;
        }
    }

}
