package com.example.luisafarias.myapplication.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.luisafarias.myapplication.MainActivity;
import com.example.luisafarias.myapplication.R;
import com.example.luisafarias.myapplication.model.Feed;
import com.example.luisafarias.myapplication.model.FeedListAdapter;
import com.example.luisafarias.myapplication.model.Repositorio;
import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.auth.TokenAuthorization;

import java.util.List;

public class FeedListFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _token = getArguments().getString("tokenKey");
            _feed = getArguments().getParcelable("feed");
            if(_feed!=null)
            Log.d(FeedListFragment.class.getName(),_feed.get_nome());
            _authorization = new TokenAuthorization(_token);
            reloadFeeds();

        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        _view = inflater.inflate(R.layout.fragment_feed_list, container, false);
        _allFeeds = _view.findViewById(R.id.list_feeds);
        reloadFeeds();
        return _view;
    }

    public void reloadFeeds() {
        Repositorio.getInstance()
                .feedListAll(_authorization, new Repositorio.CallbackFeeds() {
                    @Override
                    public void onSuccess(List<Feed> feedList) {
                        _feedAdapter = new FeedListAdapter(
                                _view.getContext(), _authorization, feedList);
                        _allFeeds.setAdapter(_feedAdapter);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.e(MainActivity.class.getName(),e.getMessage());
                    }
                });
    }



    private ListView _allFeeds;
    private Authorization _authorization;
    private Feed _feed;
    private FeedListAdapter _feedAdapter;
    private String _token;
    private View _view;
}
