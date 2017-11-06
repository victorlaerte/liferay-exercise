package com.example.luisafarias.myapplication.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.app.Fragment;

import com.example.luisafarias.myapplication.model.Rss;
import com.example.luisafarias.myapplication.model.RssRepositorio;
import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.auth.TokenAuthorization;

import java.util.List;

/**
 * Created by luisafarias on 06/11/17.
 */

public class MyViewModel extends ViewModel {
    private MutableLiveData<Fragment> _fragmentNewRss;

    public LiveData<Fragment> getFragment(){
        if (_fragmentNewRss == null){
            _fragmentNewRss = new MutableLiveData<Fragment>();
            loadRssList();
        }

        return _fragmentNewRss;
    }

    private void loadRssList(){
       ;
    }
}
