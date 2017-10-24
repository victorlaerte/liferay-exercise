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
    RecyclerView feed_list;
    List<FeedItem> feedItemList;
    private static String TAG = "MainActivity";
    ItemAdapter adapter = null;
    private WeRetrofitService rs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_news);

        rs = RetrofitClient.getClient("http://g1.globo.com/")
                .create(WeRetrofitService.class);
        List<FeedItem> items = new ArrayList<FeedItem>();
        feed_list = (RecyclerView)findViewById(R.id.feed_news_list);
        adapter = new ItemAdapter(this,items);
        //feed_list.setHasFixedSize(true);
        LinearLayoutManager layoutMgr = new LinearLayoutManager(this);
        feed_list.setLayoutManager(layoutMgr);
        feed_list.setAdapter(adapter);

        loadAnswers();

    }

    private void loadAnswers() {
       rs.getItems().enqueue(new Callback<Feed>() {
           @Override
           public void onResponse(Call<Feed> call, Response<Feed> response) {
               if (response.isSuccessful()){
                   String e  = response.message();
                   String s = response.toString();
                   String d = response.body().get_version();
                   String c = response.body().get_channel().getTitle();
                   //List<FeedItem> a = response.body();
                  // String testeNome = response.body().get(0).getTitle();
                   adapter.updateAnswers(response.body().get_channel().getItem());
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
}
