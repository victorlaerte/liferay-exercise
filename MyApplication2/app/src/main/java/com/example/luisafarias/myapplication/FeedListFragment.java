package com.example.luisafarias.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.luisafarias.myapplication.model.Feed;
import com.example.luisafarias.myapplication.model.FeedListAdapter;
import com.example.luisafarias.myapplication.model.Repositorio;

import java.util.List;

public class FeedListFragment extends Fragment {

    private String _token;
    private Toolbar _toolbar;
    private View _view;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        _token = getIntent().getExtras().getString("tokenKey");
        _view = inflater.inflate(R.layout.fragment_feed_list, container, false);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        _toolbar = (Toolbar) _view.findViewById(R.id.toolbar);
        activity.setSupportActionBar(_toolbar);
        return _view;
    }

    private void reloadFeeds() {
        Repositorio.getInstance()
                .feedListAll(_authorization, new Repositorio.CallbackFeeds() {
                    @Override
                    public void onSuccess(List<Feed> feedList) {
                        _feedAdapter = new FeedListAdapter(_view, _authorization, feedList);
                        _allFeeds.setAdapter(_feedAdapter);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.e(MainActivity.class.getName(),e.getMessage());
                    }
                });
    }


}
