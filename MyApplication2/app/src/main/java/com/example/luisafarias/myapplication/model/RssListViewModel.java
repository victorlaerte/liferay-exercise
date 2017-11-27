package com.example.luisafarias.myapplication.model;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.List;

/**
 * Created by luisafarias on 17/11/17.
 */

public class RssListViewModel extends ViewModel {

    public RssListViewModel(){
    }

    public List<Rss> getRssList() {
        return _rssList;
    }

    public void setRssList(List<Rss> _rssList) {
        this._rssList = _rssList;
    }

    public void deleteRss(Rss rss){
        this._rssList.remove(rss);
    }

    public void addRss(Rss rss){
        this._rssList.add(rss);
    }

    public String getSearchText() {
        return _searchText;
    }

    public void setSearchText(String _searchText) {
        this._searchText = _searchText;
    }
    
    private List<Rss> _rssList;
    private String _searchText = "";
}
