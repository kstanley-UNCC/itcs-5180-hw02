/*
 * Group 22 Homework 02
 * MainActivity.java
 * Ken Stanley & Stephanie Karp
 */
package com.example.group22_hw02.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group22_hw02.Drink;
import com.example.group22_hw02.Profile;
import com.example.group22_hw02.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Comparable<MainActivity> {
    TextView bacLevelView;
    TextView numDrinksView;
    TextView statusView;
    TextView weightView;
    Button buttonDrinkAdd;
    Button buttonDrinkView;
    Button buttonSetWeight;
    Button buttonReset;

    Profile profile;

    static final public int ACTIVITY_SET = 0;
    static final public int ACTIVITY_DRINK_ADD = 1;

    public ArrayList<Drink> drinkArrayList = new ArrayList<>();

    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result != null && result.getData() != null) {
                Intent intent = result.getData();

                switch (result.getResultCode()) {
                    case ACTIVITY_SET:
                        profile = (Profile) intent.getSerializableExtra(getString(R.string.intent_profile));

                        if (profile.weight > 0) {
                            weightView.setText(getString(R.string.weight_view_label_label, profile.weight, profile.gender));
                        }
                        break;
                    case ACTIVITY_DRINK_ADD:
                        // takes entered drink and adds it to the ArrayList
                        Drink drink = (Drink) result.getData().getSerializableExtra(getString(R.string.intent_drink));
                        drinkArrayList.add(drink);

                        numDrinksView.setText(getString(R.string.num_drinks_view, drinkArrayList.size()));
                        bacLevelView.setText(getString(R.string.bac_level_view, calculateBac()));
                        break;
                    default:
                        throw new IllegalArgumentException(getString(R.string.exception_illegal_argument_unknown_result_code, result.getResultCode()));
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.app_name);

        bacLevelView = findViewById(R.id.bacLevelView);
        numDrinksView = findViewById(R.id.numDrinksView);
        statusView = findViewById(R.id.statusView);
        buttonDrinkAdd = findViewById(R.id.buttonDrinkAdd);
        buttonDrinkView = findViewById(R.id.buttonDrinkView);
        buttonSetWeight = findViewById(R.id.buttonSetWeight);
        buttonReset = findViewById(R.id.buttonReset);
        weightView = findViewById(R.id.weightView);

        // reset button resets calculator to original state
        findViewById(R.id.buttonReset).setOnClickListener(v -> {
            weightView.setText(R.string.weight_view_label_reset);
            numDrinksView.setText(getString(R.string.num_drinks_view, 0));
            bacLevelView.setText(getString(R.string.bac_level_view, 0.0));

            drinkArrayList.clear();

            buttonDrinkAdd.setEnabled(false);
            buttonDrinkView.setEnabled(false);
            buttonReset.setEnabled(false);
        });
        findViewById(R.id.buttonReset).performClick();

        // button to set weight and gender
        findViewById(R.id.buttonSetWeight).setOnClickListener(v -> {
            Intent intent = new Intent("com.example.group22_hw02.activities.SetActivity");
            startForResult.launch(intent);
        });

        // button to view drinks
        findViewById(R.id.buttonDrinkView).setOnClickListener(v -> {
            if (drinkArrayList.isEmpty()) {
                Toast.makeText(getApplicationContext(), "There are no drinks to view", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(getApplicationContext(), ViewDrinksActivity.class);
                startForResult.launch(intent);
            }
        });

        // button to add drinks
        findViewById(R.id.buttonDrinkAdd).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AddDrinkActivity.class);
            startForResult.launch(intent);
        });

        // This only needs to be added once.
        bacLevelView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    double bac = Double.parseDouble(charSequence.toString());

                    GradientDrawable viewBackground = (GradientDrawable) statusView.getBackground();

                    if (0 <= bac && bac <= 0.08) {
                        // You're Safe.
                        statusView.setText(R.string.status_view_safe);
                        viewBackground.setColor(getResources().getColor(R.color.success));
                    } else if (0.08 < bac && bac <= 0.2) {
                        // Be Careful.
                        statusView.setText(R.string.status_view_careful);
                        viewBackground.setColor(getResources().getColor(R.color.warning));
                    } else if (0.2 < bac) {
                        // Over the limit!
                        statusView.setText(R.string.status_view_danger);
                        viewBackground.setColor(getResources().getColor(R.color.danger));

                        if (0.25 <= bac) {
                            Toast.makeText(getParent(), R.string.status_view_over, Toast.LENGTH_SHORT).show();
                            buttonDrinkAdd.setEnabled(false);
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // if a profile was entered, enable the Add Drink button
        if (profile != null) {
            buttonDrinkAdd.setEnabled(true);
            buttonReset.setEnabled(true);
        }

        // if there are drinks in the ArrayList, enable the View Drinks button
        buttonDrinkView.setEnabled(!drinkArrayList.isEmpty());
    }

    @Override
    public int compareTo(MainActivity o) {
        return 0;
    }

    /**
     * Helper to calculate the Blood Alcohol Content for the subject.
     *
     * @return double
     */
    private double calculateBac() {
        double totalLiquidOz = 0.0;

        for (Drink drink : drinkArrayList) {
            totalLiquidOz += drink.getLiquidOunces();
        }

        return (totalLiquidOz * 5.14) / (profile.weight * profile.getGenderConstant(this));
    }
}