package com.example.luisafarias.myapplication.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by luisafarias on 29/11/17.
 */

public class RssModel extends RealmObject {

    @PrimaryKey
    private String _id;

    private ChannelModel _channelModel;

    @Required
    private String _url;

    @Required
    private String _userId;

    public String getId() {
        return _id;
    }

    public ChannelModel getChannelModel() {
        return _channelModel;
    }

    public String getUrl() {
        return _url;
    }

    public String getUserId() {
        return _userId;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public void setChannelModel(ChannelModel _channelModel) {
        this._channelModel = _channelModel;
    }

    public void setUrl(String _url) {
        this._url = _url;
    }

    public void setUserId(String _userId) {
        this._userId = _userId;
    }
}
