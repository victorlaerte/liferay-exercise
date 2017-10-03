package com.example.luisafarias.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wedeploy.android.exception.WeDeployException;

import org.json.JSONException;

import com.example.luisafarias.myapplication.model.Feed;

public class NewUrlActivity extends AppCompatActivity {
    //TODO estudar fragments

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_url);
        _userId = getIntent().getExtras().getString("userId");

    }

    public void addNewUrl(View view) throws JSONException, WeDeployException {
        Intent intent = new Intent(this,MainActivity.class);

        TextView textViewNomeURL = (TextView) findViewById(R.id.nomeUrl);
        TextView textViewUrl = (TextView) findViewById(R.id.url);

        String url = textViewUrl.getText().toString();
        String nomeUrl = textViewNomeURL.getText().toString();

        Feed _feed = new Feed(nomeUrl,url,_userId);
        intent.putExtra("feed", _feed);
        setResult(1, intent);
        finish();
    }

    private String _userId;
}
