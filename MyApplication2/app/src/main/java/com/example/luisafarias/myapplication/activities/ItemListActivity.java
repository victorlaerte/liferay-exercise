package com.example.luisafarias.myapplication.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.luisafarias.myapplication.R;
import com.example.luisafarias.myapplication.adapters.ItemAdapter;
import com.example.luisafarias.myapplication.dao.RssDAO;
import com.example.luisafarias.myapplication.interfaces.WeRetrofitService;
import com.example.luisafarias.myapplication.model.Item;
import com.example.luisafarias.myapplication.model.RetrofitClient;
import com.example.luisafarias.myapplication.model.Rss;
import com.example.luisafarias.myapplication.model.RssListViewModel;
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

		Toolbar myToolbar = findViewById(R.id.toolbar2);
		setSupportActionBar(myToolbar);

		Bundle data = getIntent().getBundleExtra(Constants.RSS);
		_rss = data.getParcelable(Constants.RSS);

        _rssListViewModel = ViewModelProviders.
                of(this).get(RssListViewModel.class);

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
			_adapter.setFeedItemsAux(_rss.getChannel().getItem());
		}
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_rss,menu);

        _menuItem = menu.findItem(R.id.search);
        _searchView = (SearchView) _menuItem.getActionView();
        _searchView.setQueryHint(getString(R.string.buscar));

        if (!_rss.getChannel().getItem().isEmpty()) {
            _menuItem.expandActionView();
            _searchView.setQuery(_rss.getChannel().getTitle(),
                    true);//entender o booleano
            _adapter.getFilter()
                    .filter(_rssListViewModel.getSearchText());

            query();
        } else {
            _menuItem.collapseActionView();
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.search:
                query();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void query() {
        _searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        _adapter.getFilter().filter(newText);
                        _rssListViewModel.setSearchText(newText);

                        return false;
                    }
                });
    }

    private void loadAnswers() {

		_rsfit.getItems(_rss.getURLEndPoint()).enqueue(new Callback<Rss>() {
			@Override
			public void onResponse(Call<Rss> call, Response<Rss> response) {
				if (response.isSuccessful()) {
					_adapter.updateAnswers(
						response.body().getChannel().getItem());
					_adapter.setFeedItemsAux(response.body().getChannel().getItem());
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

    private MenuItem _menuItem;
	private Rss _rss;
	private RecyclerView recyclerView;
    private SearchView _searchView;
	private SwipeRefreshLayout _swipeRLayout;
	private List<Item> _itemList;
	private ItemAdapter _adapter = null;
    RssListViewModel _rssListViewModel;
	private WeRetrofitService _rsfit;
	final private String CLASS_NAME = "ItemListActivity";
}
