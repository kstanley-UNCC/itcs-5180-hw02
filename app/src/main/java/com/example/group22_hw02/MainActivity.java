/*
 * Group 22 Homework 02
 * MainActivity.java
 * Ken Stanley & Stephanie Karp
 */
package com.example.group22_hw02;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Comparable<MainActivity> {
    TextView bacLevelView;
    TextView numDrinksView;
    TextView statusView;
    TextView weightView;
    Button buttonDrinkAdd;
    Button buttonDrinkView;
    Button buttonSetWeight;

    Profile profile;

    public ArrayList<Drink> drinkArrayList = new ArrayList<>();

    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result != null && result.getResultCode() == RESULT_OK) {
            if (result.getData() != null) {
                Intent intent = result.getData();

                profile = (Profile) intent.getSerializableExtra(getString(R.string.intent_profile));
                if (profile.weight > 0) {
                    weightView.setText(getString(R.string.weight_view_label_label, profile.weight, profile.gender));
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
        weightView = findViewById(R.id.weightView);

        // reset button resets calculator to original state
        findViewById(R.id.buttonReset).setOnClickListener(v -> {
            weightView.setText(R.string.weight_view_label_reset);
            numDrinksView.setText(getString(R.string.num_drinks_view, 0));
            bacLevelView.setText(getString(R.string.bac_level_view, 0.0));

            drinkArrayList.clear();

            buttonDrinkAdd.setEnabled(false);
            buttonDrinkView.setEnabled(false);
        });
        findViewById(R.id.buttonReset).performClick();

        // button to set weight and gender
        findViewById(R.id.buttonSetWeight).setOnClickListener(v -> {
            Intent intent = new Intent("com.example.group22_hw02.SetActivity");
            startForResult.launch(intent);
        });

        // button to view drinks
        findViewById(R.id.buttonDrinkView).setOnClickListener(v -> {
            if (drinkArrayList.isEmpty()) {
                Toast.makeText(getApplicationContext(), "There are no drinks to view", Toast.LENGTH_SHORT).show();
            } else {
                Intent viewIntent = new Intent(getApplicationContext(), ViewDrinksActivity.class);
                startForResult.launch(viewIntent);
            }
        });

        // button to add drinks
        findViewById(R.id.buttonDrinkAdd).setOnClickListener(v -> {
            Intent addIntent = new Intent(getApplicationContext(), AddDrinkActivity.class);
            startForResult.launch(addIntent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public int compareTo(MainActivity o) {
        return 0;
    }
}