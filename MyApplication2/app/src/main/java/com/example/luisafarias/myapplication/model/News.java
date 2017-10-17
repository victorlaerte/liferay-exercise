package com.example.luisafarias.myapplication.model;

/**
 * Created by luisafarias on 17/10/17.
 */

public class News {

    public News(String title, String link, String description){
        set_newsTitle(title);
        setLink(link);
        set_newsDescription(description);
    }

    public String get_newsDescription() {return _newsDescription;}

    public String get_newsTitle() {return _newsTitle;}

    public String getLink() {return link;}

    public void set_newsDescription(String _newsDescription) {
        this._newsDescription = _newsDescription;}

    public void set_newsTitle(String _newsTitle) {
        this._newsTitle = _newsTitle;
    }

    public void setLink(String link) {this.link = link;}

    private String _newsTitle;
    private String link;
    private String _newsDescription;

}
