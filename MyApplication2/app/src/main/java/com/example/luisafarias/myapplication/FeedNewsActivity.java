package com.example.luisafarias.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.luisafarias.myapplication.interfaces.WeRetrofitService;
import com.example.luisafarias.myapplication.model.Feed;
import com.example.luisafarias.myapplication.model.FeedItem;
import com.example.luisafarias.myapplication.adapters.ItemAdapter;
import com.example.luisafarias.myapplication.model.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedNewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_news);

        _rs = RetrofitClient.getClient("http://g1.globo.com/")
                .create(WeRetrofitService.class);
        _feedItemList = new ArrayList<FeedItem>();
        _feed_list = (RecyclerView)findViewById(R.id.feed_news_list);
        _adapter = new ItemAdapter(this,_feedItemList);
        //feed_list.setHasFixedSize(true);
        LinearLayoutManager layoutMgr = new LinearLayoutManager(this);
        _feed_list.setLayoutManager(layoutMgr);
        _feed_list.setAdapter(_adapter);

        loadAnswers();

    }

    private void loadAnswers() {
       _rs.getItems().enqueue(new Callback<Feed>() {
           @Override
           public void onResponse(Call<Feed> call, Response<Feed> response) {
               if (response.isSuccessful()){
                   _adapter.updateAnswers(response.body().get_channel().getItem());
                   Log.d("FeedNewsActivity","posts loaded from API");
               }
           }

           @Override
           public void onFailure(Call<Feed> call, Throwable t) {
               Log.e("FeedNewsActivity", t.getMessage());
               Log.d("MainActivity", "error loading from API");
           }
       });
    }

    private RecyclerView _feed_list;
    private List<FeedItem> _feedItemList;
    private ItemAdapter _adapter = null;
    private WeRetrofitService _rs;
}
