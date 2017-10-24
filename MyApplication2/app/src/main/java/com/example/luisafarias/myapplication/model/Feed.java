package com.example.luisafarias.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by luisafarias on 26/09/17.
 */
@Root(name = "rss", strict = false)
public class Feed implements Parcelable {

    public Feed(){} //para desserializar precisa do construtor vazio public

    public Feed(String nome, String url, String userId, Channel channel){
        set_title(nome);
        set_url(url);
        set_userId(userId);
        set_channel(channel);
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


    public String get_userId(){ return this._userId;}

    public String get_id(){ return this._id;}

    public Channel get_channel() {return _channel;}

    public String get_version() {return _version;}

    public void set_channel(Channel channel) {
        this._channel = channel;
    }

    public void set_title(String _title){ this._title = _title;}

    public void set_url(String _url){ this._url = _url;}

    public void set_id(String _id){ this._id = _id;}

    private void set_userId(String _userId) {this._userId = _userId;}

    public String getPartMain(){
        int a = 0;
        if (_url.contains(".br")){
            a = _url.indexOf(".br/");
        } else if ( _url.contains(".com/")){
            a = _url.indexOf(".com/");
        }
        return _url.substring(0,(a+5));
    }

    public String getPartXml(){
        int a = 0;
        if (_url.contains(".br")){
            a = _url.indexOf(".br/");
        } else if ( _url.contains(".com/")){
            a = _url.indexOf(".com/");
        }
        return _url.substring((a+5));
    }

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

    @Override
    public String toString() {
        return "RSS{" +
                "version ='" + _version + '\''+
                " channel=" + _channel +
                '}';
    }
    @Attribute(name = "version")
    private String _version;
    @Element(name = "channel")
    private Channel _channel;
    private String _description;
    private String _id;
    private String _image;
    private String _title;
    private String _url;
    private String _userId;
}
