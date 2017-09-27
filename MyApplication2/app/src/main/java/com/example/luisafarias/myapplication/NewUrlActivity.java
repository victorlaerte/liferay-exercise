package com.example.luisafarias.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wedeploy.android.Callback;
import com.wedeploy.android.WeDeploy;
import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.auth.TokenAuthorization;
import com.wedeploy.android.exception.WeDeployException;
import com.wedeploy.android.transport.Response;

import org.json.JSONException;
import org.json.JSONObject;

import model.Feed;
import model.Repositorio;

public class NewUrlActivity extends AppCompatActivity {
    //Repositorio repositorio = new Repositorio();
    String token, userId;
    Authorization authorization;
    Feed feed;

    WeDeploy weDeploy = new WeDeploy.Builder().build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_url);
        token = getIntent().getExtras().getString("token");
        userId = getIntent().getExtras().getString("userId");
        authorization = new TokenAuthorization(token);


    }

    public void addNewUrl(View view) throws JSONException, WeDeployException {

        TextView nomeUrl1 = (TextView) findViewById(R.id.nomeUrl);
        String nomeUrl = nomeUrl1.getText().toString();
        TextView url1 = (TextView) findViewById(R.id.url);
        String url = url1.getText().toString();
        feed = new Feed(nomeUrl,url,userId);
        Repositorio.getInstance(this).addFeed(feed,authorization);

    }
}
