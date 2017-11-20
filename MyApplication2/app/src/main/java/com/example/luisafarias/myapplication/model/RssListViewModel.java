package com.example.luisafarias.myapplication.model;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by luisafarias on 17/11/17.
 */

public class RssListViewModel extends ViewModel {

    public RssListViewModel(){
    }

    private List<Rss> _rssList;
}
