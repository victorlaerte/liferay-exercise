package com.example.luisafarias.myapplication.model;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by luisafarias on 29/11/17.
 */

public class RssModel extends RealmObject {

    @PrimaryKey
    private String _id;

    @Required
    private String _channelTitle;

    @Required
    private RealmList<String> _itemListTitle;

    private String _url;

    private String _userId;

    @Required
    private Boolean _favorite;

    public Boolean getFavorite() {
        return _favorite;
    }

    public void setFavorite(Boolean _favorite) {
        this._favorite = _favorite;
    }

    public String getId() {
        return _id;
    }

    public String getUrl() {
        return _url;
    }

    public String getUserId() {
        return _userId;
    }

    public String getChannelTitle() {
        return _channelTitle;
    }

    public RealmList<String> getItemListTitle() {
        return _itemListTitle;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public void setUrl(String _url) {
        this._url = _url;
    }

    public void setUserId(String _userId) {
        this._userId = _userId;
    }

    public void setChannelTitle(String _channelTitle) {
        this._channelTitle = _channelTitle;
    }

    public void setItemListTitle(RealmList<String> _itemListTitle) {
        this._itemListTitle = _itemListTitle;
    }
}
