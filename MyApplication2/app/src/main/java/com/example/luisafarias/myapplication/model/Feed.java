package com.example.luisafarias.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by luisafarias on 26/09/17.
 */

public class Feed implements Parcelable {

    public Feed(){}

    public Feed(String nome, String url, String userId){
        set_title(nome);
        set_url(url);
        set_userId(userId);
    }

    protected Feed(Parcel in) {
        _title = in.readString();
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

    public String get_title(){ return this._title;}

    public String get_url(){ return this._url;}

    public String get_description(){ return this._description;}

    public String get_image() {return _image;}

    public String get_userId(){ return this._userId;}

    public String get_id(){ return this._id;}

    public void set_title(String _title){ this._title = _title;}

    public void set_url(String _url){ this._url = _url;}

    public void set_image(String _image) {this._image = _image;}

    public void set_description(String description) {this._description = description;}

    public void set_id(String _id){ this._id = _id;}

    private void set_userId(String _userId) {this._userId = _userId;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_title);
        parcel.writeString(_url);
        parcel.writeString(_userId);
        parcel.writeString(_id);
    }
    private String _description;
    private String _id;
    private String _image;
    private String _title;
    private String _url;
    private String _userId;
    private List<News> newsList;
}
