package com.example.luisafarias.myapplication.model;

import android.app.ActionBar;
import android.arch.lifecycle.ViewModel;

/**
 * Created by luisafarias on 27/11/17.
 */

public class MainViewModel extends ViewModel {

    public MainViewModel(){

    }

    public void setActionBar(ActionBar _actionBar) {
        this._actionBar = _actionBar;
    }

    public ActionBar getActionBar() {
        return _actionBar;
    }

    private ActionBar _actionBar;
}
