package com.example.luisafarias.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SemFeed extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem_feed);
    }

    public void addNewUrl(View view){
        Intent intent = new Intent(this,NewUrl.class);
        startActivity(intent);
    }
}
