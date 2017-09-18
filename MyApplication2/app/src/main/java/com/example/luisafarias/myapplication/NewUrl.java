package com.example.luisafarias.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NewUrl extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_url);
    }

    public void addNewUrl(View view){
        TextView nomeUrl = (TextView) findViewById(R.id.nomeUrl);
        String nomeurl = nomeUrl.getText().toString();
        TextView url1 = (TextView) findViewById(R.id.url);
        String url = url1.getText().toString();


    }
}
