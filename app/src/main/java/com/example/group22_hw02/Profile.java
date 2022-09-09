package com.example.group22_hw02;

import java.io.Serializable;

public class Profile implements Serializable {
    public final int weight;
    public final String gender;
    public final double genderConst;

    public Profile(int weight, String gender, double genderConst) {
        this.weight = weight;
        this.gender = gender;
        this.genderConst = genderConst;
    }
}
