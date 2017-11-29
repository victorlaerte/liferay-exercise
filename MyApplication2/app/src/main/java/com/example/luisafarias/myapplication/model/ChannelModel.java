package com.example.luisafarias.myapplication.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by luisafarias on 29/11/17.
 */

public class ChannelModel extends RealmObject {

    public int getId() {
        return _id;
    }

    public String getTitle() {
        return _title;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public void setTitle(String _title) {
        this._title = _title;
    }

    @PrimaryKey
    private int _id;

    @Required
    private String _title;
}
