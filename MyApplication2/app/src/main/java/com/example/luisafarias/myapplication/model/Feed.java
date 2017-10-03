package com.example.luisafarias.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luisafarias on 26/09/17.
 */

public class Feed implements Parcelable {

    public Feed(){}

    public Feed(String nome, String url, String userId){
        this._nome = nome;
        this._url = url;
        this._userId = userId;
    }

    protected Feed(Parcel in) {
        _nome = in.readString();
        _url = in.readString();
        _userId = in.readString();
        _id = in.readString();
    }

    public static final Creator<Feed> CREATOR = new Creator<Feed>() {
        @Override
        public Feed createFromParcel(Parcel in) {
            return new Feed(in);
        }

        @Override
        public Feed[] newArray(int size) {
            return new Feed[size];
        }
    };

    public String get_nome(){ return this._nome;}

    public String get_url(){ return this._url;}

    public void set_nome(String _nome){ this._nome = _nome;}

    public void set_url(String _url){ this._url = _url;}

    public String get_userId(){ return this._userId;}

    public String get_id(){ return this._id;}

    public void set_id(String _id){ this._id = _id;}

    //public void set_userId(String _userId){ this._userId = _userId;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_nome);
        parcel.writeString(_url);
        parcel.writeString(_userId);
        parcel.writeString(_id);
    }

    private String _id;
    private String _nome;
    private String _url;
    private String _userId;
}
