package com.example.group22_hw02;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.Objects;

public class Profile implements Serializable {
    public final int weight;
    public final String gender;

    public static final double BAC_GENDER_FEMALE = 0.66;
    public static final double BAC_GENDER_MALE = 0.73;

    public Profile(int weight, String gender) {
        this.weight = weight;
        this.gender = gender;
    }

    public double getGenderConstant(AppCompatActivity app) {
        if (Objects.equals(app.getString(R.string.gender_group_female), gender)) {
            return BAC_GENDER_FEMALE;
        } else if (Objects.equals(app.getString(R.string.gender_group_male), gender)) {
            return BAC_GENDER_MALE;
        } else {
            throw new IllegalStateException(app.getString(R.string.exception_illegal_state_gender));
        }
    }
}
