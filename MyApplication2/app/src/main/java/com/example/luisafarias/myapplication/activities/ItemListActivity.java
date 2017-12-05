package com.example.luisafarias.myapplication.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.example.luisafarias.myapplication.R;
import com.example.luisafarias.myapplication.adapters.ItemAdapter;
import com.example.luisafarias.myapplication.dao.RssDAO;
import com.example.luisafarias.myapplication.interfaces.WeRetrofitService;
import com.example.luisafarias.myapplication.model.Item;
import com.example.luisafarias.myapplication.model.RetrofitClient;
import com.example.luisafarias.myapplication.model.Rss;
import com.example.luisafarias.myapplication.model.RssModel;
import com.example.luisafarias.myapplication.util.AndroidUtil;
import com.example.luisafarias.myapplication.util.Constants;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.List;
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

		_swipeRLayout = findViewById(R.id.swiperefresh_item);
		recyclerView = findViewById(R.id.feed_news_list);

		if (AndroidUtil.isOnline(this)) {
			_rsfit = RetrofitClient.getInstance(_rss.getURLHost())
				.create(WeRetrofitService.class);
		}
		_itemList = new ArrayList();
		_adapter = new ItemAdapter(this, _itemList);

		LinearLayoutManager layoutMgr = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutMgr);
		recyclerView.setAdapter(_adapter);

		if (AndroidUtil.isOnline(this)) {
			loadAnswers();
			_swipeRLayout.setOnRefreshListener(
					() -> loadAnswers());
		} else {
			_adapter.updateAnswers(_rss.getChannel().getItem());
		}
	}

	private void loadAnswers() {

		_rsfit.getItems(_rss.getURLEndPoint()).enqueue(new Callback<Rss>() {
			@Override
			public void onResponse(Call<Rss> call, Response<Rss> response) {
				if (response.isSuccessful()) {
					_adapter.updateAnswers(
						response.body().getChannel().getItem());
					RssDAO.getInstance().addRssRealm(response.body(), _rss);
					Log.d(CLASS_NAME, getString(R.string.itens_atualizados));
				}

				_swipeRLayout.setRefreshing(false);
			}

			@Override
			public void onFailure(Call<Rss> call, Throwable t) {
				Log.e(CLASS_NAME, t.getMessage());
				_swipeRLayout.setRefreshing(false);
				Snackbar.make(findViewById(R.id.swiperefresh_item),
					R.string.erro_itens_atualizados, Snackbar.LENGTH_LONG)
					.show();
			}
		});
	}

	private Rss _rss;
	private RecyclerView recyclerView;
	private SwipeRefreshLayout _swipeRLayout;
	private List<Item> _itemList;
	private ItemAdapter _adapter = null;
	private WeRetrofitService _rsfit;
	final private String CLASS_NAME = "ItemListActivity";
}
