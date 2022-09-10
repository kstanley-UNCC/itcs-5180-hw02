package com.example.group22_hw02;

import java.io.Serializable;

public class Profile implements Serializable {
    public final int weight;
    public final String gender;

    public static final double BAC_GENDER_FEMALE = 0.66;
    public static final double BAC_GENDER_MALE = 0.73;

    public Profile(int weight, String gender) {
        this.weight = weight;
        this.gender = gender;
    }
}
