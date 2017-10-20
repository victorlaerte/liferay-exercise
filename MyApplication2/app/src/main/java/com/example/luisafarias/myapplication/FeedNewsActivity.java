package com.example.luisafarias.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.luisafarias.myapplication.model.FeedItem;

import java.util.ArrayList;
import java.util.List;

public class FeedNewsActivity extends AppCompatActivity {
    RecyclerView feed_list;
    List<FeedItem> feedItemList;
    private static String TAG = "MainActivity";
    ItemAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_news);

        feed_list = (RecyclerView)findViewById(R.id.feed_news_list);
        feed_list.setHasFixedSize(true);
        LinearLayoutManager layoutMgr = new LinearLayoutManager(this);
        feed_list.setLayoutManager(layoutMgr);

        List<FeedItem> items = new ArrayList<FeedItem>();
        adapter = new ItemAdapter(items);
        feed_list.setAdapter(adapter);
        startService();

    }

    private void startService() {
       // Intent intent = new Intent(getApplicationContext(),)
    }
}
