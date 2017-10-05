package com.example.luisafarias.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.luisafarias.myapplication.model.Feed;


public class NewFeedFragment extends Fragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _userId = getArguments().getString("userId");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(NewFeedFragment.class.getName(),"ele esta aqui");
        // Inflate the layout for this fragment
        _view = inflater.inflate(R.layout.fragment_new_feed, container, false);
        return _view;
    }

    public void addNewFeed(View view){

        EditText newUrlName = _view.findViewById(R.id.newNameFeed);
        EditText newUrl = _view.findViewById(R.id.newUrlFeed);

        String name = newUrlName.getText().toString();
        String url = newUrl.getText().toString();

        Feed feed = new Feed(name,url,_userId);
    }

    private String _userId;
    private View _view;

}
