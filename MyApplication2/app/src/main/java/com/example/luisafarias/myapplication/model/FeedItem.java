package com.example.luisafarias.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by luisafarias on 19/10/17.
 */
@Root(name = "item", strict = false)
public class FeedItem implements Parcelable {

    @Element(name = "title", required = true)
    private String _title;
    @Element(name = "pubDate", required = true)
    private String _publicationDate;
    @Element(name = "description", required = true)
    private String _description;

    public FeedItem(String title, String description, String publicationDate){
        this._title = title;
        this._description = description;
        this._publicationDate = publicationDate;
    }

    public FeedItem(){

    }

    public String get_title() {
        return _title;
    }

    public String get_description() {
        return _description;
    }

    public String get_publicationDate() {
        return _publicationDate;
    }

    public boolean isEqualTo(FeedItem o){
        if (o.get_title().equals(this._title)&&
                o.get_description().equals(this._description)&&
                o.get_publicationDate().equals(this._publicationDate)){
            return true;
        }
        else
            return false;
    }

    protected FeedItem(Parcel in) {
    }

    public static final Creator<FeedItem> CREATOR = new Creator<FeedItem>() {
        @Override
        public FeedItem createFromParcel(Parcel in) {
            return new FeedItem(in);
        }

        @Override
        public FeedItem[] newArray(int size) {
            return new FeedItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
