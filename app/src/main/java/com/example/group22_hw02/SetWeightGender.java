/*
 * Group 22 Homework 02
 * SetWeightGender.java
 * Ken Stanley & Stephanie Karp
 */
package com.example.group22_hw02;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SetWeightGender extends AppCompatActivity {
    EditText weightWidget;
    RadioGroup genderGroup;

    int userWeight;
    double genderConstant;
    String gender;

    protected final double BAC_GENDER_FEMALE = 0.66;
    protected final double BAC_GENDER_MALE = 0.73;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setweightgender);

        setTitle(R.string.app_activity_set_weight_gender_name);

        genderGroup = findViewById(R.id.genderGroup);
        weightWidget = findViewById(R.id.weightWidget);

        // Cancel button returns to MainActivity
        findViewById(R.id.buttonCancel).setOnClickListener(v -> finish());

        // set weight button
        findViewById(R.id.buttonWeightSet).setOnClickListener(v -> {
            if (!validateWeight()) {
                Toast.makeText(getApplicationContext(), getString(R.string.validation_weight), Toast.LENGTH_SHORT).show();
                return;
            }

            if (!validateGender()) {
                Toast.makeText(getApplicationContext(), getString(R.string.validation_gender), Toast.LENGTH_SHORT).show();
                return;
            }

            setWeight();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

            setResult(RESULT_OK, intent);

            finish();
        });
    }

    public void setWeight() {
        String value = weightWidget.getText().toString();
        userWeight = Integer.parseInt(value);

        int genderChoice = genderGroup.getCheckedRadioButtonId();

        // select gender
        if (genderChoice == R.id.genderFemale) {
            gender = getString(R.string.gender_group_female);
            genderConstant = BAC_GENDER_FEMALE;
        } else if (genderChoice == R.id.genderMale) {
            gender = getString(R.string.gender_group_male);
            genderConstant = BAC_GENDER_MALE;
        } else {
            throw new IllegalStateException(getString(R.string.exception_illegal_state_gender));
        }
    }

    /**
     * Validate the following rules:
     *
     * 1. The gender is either male or female.
     *
     * @return boolean
     */
    @SuppressLint("NonConstantResourceId")
    private boolean validateGender() {
        switch (genderGroup.getCheckedRadioButtonId()) {
            case R.id.genderFemale:
            case R.id.genderMale:
                return true;
            default:
                return false;
        }
    }

    /**
     * Validate the following rules:
     *
     * 1. The number entered is valid.
     * 2. The number entered is greater than 0.
     *
     * @return boolean
     */
    private boolean validateWeight() {
        try {
            int weight = Integer.parseInt(weightWidget.getText().toString());
            return weight > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
