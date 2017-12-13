package com.example.luisafarias.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by luisafarias on 26/09/17.
 */
@Root(name = "rss", strict = false)
public class Rss implements Parcelable {

    public Rss() {
    } //para desserializar precisa do construtor vazio public

    public Rss(String url, String userId, Channel channel) {
        setUrl(url);
        setUserId(userId);
        setChannel(channel);
    }

    protected Rss(Parcel in) {
        _url = in.readString();
        _userId = in.readString();
        _id = in.readString();
        _channel = (Channel) in.readValue(Channel.class.getClassLoader());
    }

    public static final Creator<Rss> CREATOR = new Creator<Rss>() {
        @Override
        public Rss createFromParcel(Parcel in) {
            return new Rss(in);
        }

        @Override
        public Rss[] newArray(int size) {
            return new Rss[size];
        }
    };

    public String getUrl() {
        return this._url;
    }

    public String getUserId() {
        return this._userId;
    }

    public String getId() {
        return this._id;
    }

    public Channel getChannel() {
        return _channel;
    }

    public String getVersion() {
        return _version;
    }

    public void setChannel(Channel channel) {
        this._channel = channel;
    }

    public void setUrl(String _url) {
        this._url = _url;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    private void setUserId(String _userId) {
        this._userId = _userId;
    }

    public Boolean getFavorite() {
        return _favorite;
    }

    public void setFavorite(Boolean _favorite) {
        this._favorite = _favorite;
    }

    public String getURLHost() {
        int a = 0;
        if (_url.contains(".br")) {
            a = _url.indexOf(".br/");
        } else if (_url.contains(".com/")) {
            a = _url.indexOf(".com/");
        }
        return _url.substring(0, (a + 5));
    }

    public String getURLEndPoint() {
        int a = 0;
        if (_url.contains(".br")) {
            a = _url.indexOf(".br/");
        } else if (_url.contains(".com/")) {
            a = _url.indexOf(".com/");
        }
        return _url.substring((a + 5));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_url);
        parcel.writeString(_userId);
        parcel.writeString(_id);
        parcel.writeValue(_channel);
    }

    @Override
    public String toString() {
        return "RSS{"
                + "version ='"
                + _version
                + '\''
                + " channel="
                + _channel
                + '}';
    }

    @Attribute(name = "version")
    private String _version;
    @Element(name = "channel")
    private Channel _channel;
    private String _description;
    private Boolean _favorite = false;
    private String _id;
    private String _image;
    private String _url;
    private String _userId;

}
