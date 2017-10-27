package com.example.luisafarias.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.example.luisafarias.myapplication.adapters.ItemAdapter;
import com.example.luisafarias.myapplication.interfaces.WeRetrofitService;
import com.example.luisafarias.myapplication.model.Rss;
import com.example.luisafarias.myapplication.model.Item;
import com.example.luisafarias.myapplication.model.RetrofitClient;
import com.example.luisafarias.myapplication.util.Constants;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemListActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle data = getIntent().getBundleExtra(Constants.RSS);
		_rss = data.getParcelable(Constants.RSS);
		setContentView(R.layout.activity_item_list);

		_rs = RetrofitClient.getInstance(_rss.getPartMain())
			.create(WeRetrofitService.class);
		_itemList = new ArrayList<Item>();
		recyclerView = (RecyclerView) findViewById(R.id.feed_news_list);
		_adapter = new ItemAdapter(this, _itemList);
		//feed_list.setHasFixedSize(true);
		LinearLayoutManager layoutMgr = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutMgr);
		recyclerView.setAdapter(_adapter);

		loadAnswers();
	}

	private void loadAnswers() {
		_rs.getItems(_rss.getPartXml()).enqueue(new Callback<Rss>() {
			@Override
			public void onResponse(Call<Rss> call, Response<Rss> response) {
				if (response.isSuccessful()) {
					_adapter.updateAnswers(
						response.body().getChannel().getItem());
					Log.d("ItemListActivity", "posts loaded from API");
				}
			}

			@Override
			public void onFailure(Call<Rss> call, Throwable t) {
				Log.e("ItemListActivity", t.getMessage());
				Log.d("MainActivity", "error loading from API");
			}
		});
	}
	private Rss _rss;
	private RecyclerView recyclerView;
	private List<Item> _itemList;
	private ItemAdapter _adapter = null;
	private WeRetrofitService _rs;
}
