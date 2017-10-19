package com.example.luisafarias.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by luisafarias on 19/10/17.
 */
@Root(strict = false)
public class Channel implements Parcelable{
    @ElementList(name = "intem", required = true, inline = true)
    private List<FeedItem> itemList;

    public List<FeedItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<FeedItem> itemList) {
        this.itemList = itemList;
    }

    public Channel(List<FeedItem> feedItems){
        setItemList(feedItems);
    }
    public Channel(){

    }

    protected Channel(Parcel in) {
    }

    public static final Creator<Channel> CREATOR = new Creator<Channel>() {
        @Override
        public Channel createFromParcel(Parcel in) {
            return new Channel(in);
        }

        @Override
        public Channel[] newArray(int size) {
            return new Channel[size];
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
