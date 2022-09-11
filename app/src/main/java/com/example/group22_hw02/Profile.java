package com.example.group22_hw02;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class Profile implements Parcelable {
    public final int weight;
    public final String gender;

    public static final double BAC_GENDER_FEMALE = 0.66;
    public static final double BAC_GENDER_MALE = 0.73;

    public Profile(int weight, String gender) {
        this.weight = weight;
        this.gender = gender;
    }

    protected Profile(Parcel in) {
        weight = in.readInt();
        gender = in.readString();
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    public double getGenderConstant(AppCompatActivity app) {
        if (Objects.equals(app.getString(R.string.gender_group_female), gender)) {
            return BAC_GENDER_FEMALE;
        } else if (Objects.equals(app.getString(R.string.gender_group_male), gender)) {
            return BAC_GENDER_MALE;
        } else {
            throw new IllegalStateException(app.getString(R.string.exception_illegal_state_gender));
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(weight);
        parcel.writeString(gender);
    }
}
