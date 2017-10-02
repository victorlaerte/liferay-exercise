package com.example.luisafarias.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.auth.TokenAuthorization;

import org.json.JSONException;

import model.Feed;
import model.Repositorio;

public class PopUpActivity extends AppCompatActivity {
    String token;
    Feed feed;
    Authorization authorization;

    //TODO Use Dialog instead Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        token = getIntent().getExtras().getString("token");
        feed = getIntent().getExtras().getParcelable("feed");
        authorization = new TokenAuthorization(token);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8),(int)(height*.6));
    }

    public void deleteFeed(View view){
        if (feed != null && authorization != null){
            Repositorio.getInstance(this).removeFeed(feed,authorization);
        }

    }

    public void updateFeed(View view) throws JSONException {
        if (feed != null && authorization != null){
            Repositorio.getInstance(this).updateFeed(feed,authorization);
        }

    }
}
