package com.example.luisafarias.myapplication.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.luisafarias.myapplication.R;
import com.example.luisafarias.myapplication.model.Feed;
import com.example.luisafarias.myapplication.model.Repositorio;
import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.auth.TokenAuthorization;

import org.json.JSONException;


public class NewFeedFragment extends Fragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!= null) {
            _userId = getArguments().getString("userId");
            _feed = getArguments().getParcelable("feed");
            _newOredit = getArguments().getBoolean("newOredit");
            /***testando se da pra fazer tudo dentro mesmo****/
            String token = getArguments().getString("token");
            authorization = new TokenAuthorization(token);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(NewFeedFragment.class.getName(),"ele esta aqui");
        // Inflate the layout for this fragment
        _view = inflater.inflate(R.layout.fragment_new_feed, container, false);
        if (_newOredit){
            _nome = _view.findViewById(R.id.newNameFeed);
            _url = _view.findViewById(R.id.newUrlFeed);
            _nome.setText(_feed.get_nome());
            _url.setText(_feed.get_url());

            Button editSave = _view.findViewById(R.id.save);
            editSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        updateFeed();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        return _view;
    }

    public void updateFeed() throws JSONException {
        Feed feed = _feed;
        feed.set_nome(_nome.getText().toString());
        feed.set_url(_url.getText().toString());
        Repositorio.getInstance().updateFeed(feed, authorization, new Repositorio.CallbackFeed() {
            @Override
            public void onSuccess(Feed feed) {

            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    private Feed _feed;
    private boolean _newOredit = false;
    private EditText _nome;
    private String _userId;
    private View _view;
    private EditText _url;
    /***test***/
    Authorization authorization;

}
