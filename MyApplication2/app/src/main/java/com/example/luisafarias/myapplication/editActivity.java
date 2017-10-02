package com.example.luisafarias.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.luisafarias.myapplication.model.Feed;
import com.example.luisafarias.myapplication.model.Repositorio;
import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.auth.TokenAuthorization;

import org.json.JSONException;

public class EditActivity extends AppCompatActivity {

    private EditText nome;
    private EditText url;
    private String token;
    Feed feed;
    Authorization authorization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        feed = getIntent().getExtras().getParcelable("feed");
        token = getIntent().getExtras().getString("token");
        authorization = new TokenAuthorization(token);
        nome = (EditText) this.findViewById(R.id.nomeUrlEdit);
        url = (EditText) this.findViewById(R.id.urlEdit);
        nome.setText(feed.getNome());
        url.setText(feed.getUrl());
    }

    public void updateFeedCall(View view) throws JSONException {
        Log.d(EditActivity.class.getName(),nome.getText().toString());
        Log.d(EditActivity.class.getName(),url.getText().toString());
        feed.setNome(nome.getText().toString());
        feed.setUrl(url.getText().toString());
        Repositorio.getInstance(this).updateFeed(feed,authorization);
    }
}