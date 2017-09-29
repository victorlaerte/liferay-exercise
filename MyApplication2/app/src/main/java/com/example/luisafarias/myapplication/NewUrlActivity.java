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
    String userId;
    Feed feed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_url);
        userId = getIntent().getExtras().getString("userId");



    }

    public void addNewUrl(View view) throws JSONException, WeDeployException {
        Intent intent = new Intent(this,FeedListActivity.class);

        TextView nomeUrl1 = (TextView) findViewById(R.id.nomeUrl);
        TextView url1 = (TextView) findViewById(R.id.url);

        String url = url1.getText().toString();
        String nomeUrl = nomeUrl1.getText().toString();

        feed = new Feed(nomeUrl,url,userId);
        intent.putExtra("feed",feed);
        setResult(1,intent);
        finish();

    }
}
