package com.example.luisafarias.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Victor Oliveira
 */
public class User implements Parcelable {

    //TODO usar esta classe para armazenar informações do user

    protected User(Parcel in) {
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
