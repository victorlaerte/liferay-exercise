package com.example.luisafarias.myapplication.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.luisafarias.myapplication.R;
import com.example.luisafarias.myapplication.adapters.ItemAdapter;
import com.example.luisafarias.myapplication.interfaces.WeRetrofitService;
import com.example.luisafarias.myapplication.model.Channel;
import com.example.luisafarias.myapplication.model.Item;
import com.example.luisafarias.myapplication.model.RetrofitClient;
import com.example.luisafarias.myapplication.model.Rss;
import com.example.luisafarias.myapplication.model.RssModel;
import com.example.luisafarias.myapplication.util.Constants;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_item_list);

        Bundle data = getIntent().getBundleExtra(Constants.RSS);
        _rss = data.getParcelable(Constants.RSS);
        Channel channel = data.getParcelable(Constants.CHANNEL);
        _realm = Realm.getDefaultInstance();
        _results = _realm.where(RssModel.class).
                equalTo("_id", _rss.getId()).findAll();

        _swipeRLayout = findViewById(R.id.swiperefresh_item);
        recyclerView = findViewById(R.id.feed_news_list);

        if (isOnline()){
            _rsfit = RetrofitClient.getInstance(_rss.getURLHost())
                    .create(WeRetrofitService.class);
        }
        _itemList = new ArrayList();
        _adapter = new ItemAdapter(this, _itemList);

        LinearLayoutManager layoutMgr = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutMgr);
        recyclerView.setAdapter(_adapter);

        if (isOnline()){
            loadAnswers();
            _swipeRLayout.setOnRefreshListener(
                    new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            loadAnswers();
                        }
                    });
        }else {
            _adapter.updateAnswers(channel.getItem());
        }


    }

    private void loadAnswers() {


            _rsfit.getItems(_rss.getURLEndPoint()).enqueue(new Callback<Rss>() {
                @Override
                public void onResponse(Call<Rss> call, Response<Rss> response) {
                    if (response.isSuccessful()) {
                        _adapter.updateAnswers(
                                response.body().getChannel().getItem());
                        addRssRealm(response.body(), _rss);
                        Log.d("ItemListActivity", "posts loaded from API");
                    }

                    _swipeRLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<Rss> call, Throwable t) {
                    Log.e("ItemListActivity", t.getMessage());
                    _swipeRLayout.setRefreshing(false);
                    Snackbar.make(findViewById(R.id.swiperefresh_item),
                            "error loading from API", Snackbar.LENGTH_LONG).show();
                }
            });
    }

    private void addRssRealm(Rss rss, Rss _rss) {
        _realm.beginTransaction();
        RealmList<String> realmStringList = new RealmList<>();
        _rssModel = _realm.createObject(RssModel.class,_rss.getId());
        _rssModel.setChannelTitle(rss.getChannel().getTitle());
        for (Item item : rss.getChannel().getItem()){
            realmStringList.add(item.getTitle());
        }
        _rssModel.setItemListTitle(realmStringList);
        _realm.commitTransaction();
    }

    private List<Item> realmListToItemList(RssModel rssModel){
        _realm.beginTransaction();
        List<Item> itemList = new ArrayList<>();
        Item item = new Item();
        for(String a : rssModel.getItemListTitle()){
            item.setTitle(a);
            itemList.add(item);
        }
        _realm.commitTransaction();

        return itemList;
    }

    private boolean isOnline(){
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        return manager.getActiveNetworkInfo() != null;
    }

    private Realm _realm;
    private RealmResults<RssModel> _results;
    private RssModel _rssModel;
    private Rss _rss;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout _swipeRLayout;
    private List<Item> _itemList;
    private ItemAdapter _adapter = null;
    private WeRetrofitService _rsfit;
}
