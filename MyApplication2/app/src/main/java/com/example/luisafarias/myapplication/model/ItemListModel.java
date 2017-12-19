package com.example.luisafarias.myapplication.model;

import android.arch.lifecycle.ViewModel;

import java.util.List;

/**
 * Created by luisafarias on 12/12/17.
 */

public class ItemListModel extends ViewModel {

    public ItemListModel(){

    }

    public String getSearchText() {
        return _searchText;
    }

    public List<Item> getItemList() {
        return _itemList;
    }

    public void setSearchText(String _searchText) {
        this._searchText = _searchText;
    }

    public void setItemList(List<Item> _ItemList) {
        this._itemList = _ItemList;
    }

    private List<Item> _itemList;
    private String _searchText = "";
}
