package com.example.luisafarias.myapplication;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.luisafarias.myapplication.model.Feed;
import com.example.luisafarias.myapplication.model.Repositorio;
import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.auth.TokenAuthorization;

public class PopUpActivity extends AppCompatActivity {

    //TODO Use Dialog instead Activity


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        _token = getIntent().getExtras().getString("token");
        _feed = getIntent().getExtras().getParcelable("feed");
        _authorization = new TokenAuthorization(_token);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));
    }

    public void deleteFeed(final View view) {
        if (_feed != null && _authorization != null) {
            Repositorio.getInstance().removeFeed(_feed, _authorization, new Repositorio.CallbackFeed() {
                @Override
                public void onSuccess(Feed feed) {
                    Snackbar.make(view,"Removido",Snackbar.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Exception e) {
                    Snackbar.make(view,e.getMessage(),Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }

    public void updateFeed(View view) {
        if (_feed != null && _authorization != null) {
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra("_feed", _feed);
            intent.putExtra("_token", _token);
            startActivity(intent);
        }
    }

    private Authorization _authorization;
    private Feed _feed;
    private String _token;
//
}
