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
import android.widget.ListView;

import com.example.luisafarias.myapplication.model.Feed;
import com.example.luisafarias.myapplication.model.FeedListAdapter;
import com.example.luisafarias.myapplication.model.Repositorio;
import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.auth.TokenAuthorization;

import java.util.List;

public class FeedListFragment extends Fragment {

    private ListView _allFeeds;
    private Authorization _authorization;
    private FeedListAdapter _feedAdapter;
    private String _token;
    private View _view;
    private String _test;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _token = getArguments().getString("tokenKey");
            _authorization = new TokenAuthorization(_token);
            reloadFeeds();
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        if(savedInstanceState != null){
//            _token = getArguments().getString("tokenKey");
//            _authorization = new TokenAuthorization(_token);
//            Log.d(FeedListFragment.class.getName(),"fragment");
//        }

        _view = inflater.inflate(R.layout.fragment_feed_list, container, false);
        _allFeeds = _view.findViewById(R.id.list_feeds);
        return _view;
    }

    private void reloadFeeds() {
        Repositorio.getInstance()
                .feedListAll(_authorization, new Repositorio.CallbackFeeds() {
                    @Override
                    public void onSuccess(List<Feed> feedList) {
                        _feedAdapter = new FeedListAdapter(_view.getContext(), _authorization, feedList);
                        _allFeeds.setAdapter(_feedAdapter);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.e(MainActivity.class.getName(),e.getMessage());
                    }
                });
    }


}
