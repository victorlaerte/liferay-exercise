package com.example.luisafarias.myapplication.model;

import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by luisafarias on 23/10/17.
 */

public class AnswersResponse {

    @Root(name = "item", strict = false)
    private List<FeedItem> items = null;

    public List<FeedItem> getItems(){
        return items;
    }

    public void setItems(List<FeedItem> items){
        this.items = items;
    }
}
