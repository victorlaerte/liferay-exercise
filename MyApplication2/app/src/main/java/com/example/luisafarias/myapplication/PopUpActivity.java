package com.example.luisafarias.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.auth.TokenAuthorization;

import model.Feed;
import model.Repositorio;

public class PopUpActivity extends AppCompatActivity {
    String token;
    Feed feed;

    //TODO Use Dialog instead Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8),(int)(height*.6));
    }

    public void deleteFeed(View view){
        token = getIntent().getExtras().getString("token");
        feed = getIntent().getExtras().getParcelable("feed");
        Authorization authorization = new TokenAuthorization(token);
        Repositorio.getInstance(this).removeFeed(feed,authorization);
    }
}
