package com.example.luisafarias.myapplication.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.example.luisafarias.myapplication.R;
import com.example.luisafarias.myapplication.fragments.NewRssFragment;
import com.example.luisafarias.myapplication.fragments.RssListFragment;
import com.example.luisafarias.myapplication.model.Rss;
import com.example.luisafarias.myapplication.model.WeDeployActions;
import com.example.luisafarias.myapplication.util.Constants;
import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.auth.TokenAuthorization;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		_constraintLayout = findViewById(R.id.container);

		Toolbar myToolbar = findViewById(R.id.toolbar);
		setSupportActionBar(myToolbar);

		if (savedInstanceState == null) {

			RssListFragment rssListFragment = new RssListFragment();
			FragmentManager fm = getFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			ft.add(R.id.frame_layout_fragment, rssListFragment,
				Constants.GET_RSS_LIST_FRAGMENT);
			ft.commit();

			Bundle data = getIntent().getBundleExtra(Constants.TOKEN_USER_ID);
			_token = data.getString(Constants.TOKEN_KEY);
			_userId = data.getString(Constants.USER_ID);

			if (_userId != null) Log.d("mainReceivedUserID", _userId);

			Bundle bundle = new Bundle();
			bundle.putString(Constants.TOKEN_KEY, _token);
			rssListFragment.setArguments(bundle);

			if (_token == null) {
				throw new IllegalArgumentException();
			}
		}
		_authorization = new TokenAuthorization(_token);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.menu_rss, menu);
//
//		MenuItem myActionMenuItem = menu.findItem(R.id.search);
		//TODO: Erro de cast
		//final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
		//searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
		//	@Override
		//	public boolean onQueryTextSubmit(String query) {
		//		return false;
		//	}
		//
		//	@Override
		//	public boolean onQueryTextChange(String newText) {
		//		FragmentManager fm = getFragmentManager();
		//		RssListFragment fld = (RssListFragment) fm.findFragmentByTag(Constants.GET_RSS_LIST_FRAGMENT);
		//
		//		return false;
		//	}
		//});
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		final Intent intent = new Intent(this, LoginActivity.class);
		int id = item.getItemId();

		if (id == R.id.logout) {
			logout(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	private void logout(final Intent intent) {
		WeDeployActions.getInstance()
			.logoutUser(_token, new WeDeployActions.CallbackLogoutUser() {
				@Override
				public void onSuccess() {
					SharedPreferences sharedPref =
						getSharedPreferences(Constants.USER, MODE_PRIVATE);
					SharedPreferences.Editor editor = sharedPref.edit();
					editor.clear();
					editor.apply();
					Log.d(MainActivity.class.getName(), "saiu");
					finish();
					startActivity(intent);
				}

				@Override
				public void onFailure(Exception e) {
					Log.e("MainActivity", e.getMessage());
				}
			});
	}

	public void goAddNewRss(View view) {
		Bundle bundle = new Bundle();
		bundle.putString(Constants.TOKEN, _token);
		bundle.putString(Constants.USER_ID, _userId);
		bundle.putBoolean(Constants.NEW_OR_EDIT, false);

		Fragment fragment = new NewRssFragment();
		fragment.setArguments(bundle);

		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction =
			fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.frame_layout_fragment, fragment);
		fragmentTransaction.addToBackStack(null);//quando apertar botao de voltar ele volta para o fragment anterior
		fragmentTransaction.commit();
	}

	public void goEditRss(Rss feed) {
		Bundle bundle = new Bundle();
		bundle.putParcelable(Constants.RSS, feed);
		bundle.putBoolean(Constants.NEW_OR_EDIT, true);
		bundle.putString(Constants.TOKEN, _token);
		Fragment fragment = new NewRssFragment();
		fragment.setArguments(bundle);
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction =
			fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.frame_layout_fragment, fragment);
		fragmentTransaction.addToBackStack(
			null);//quando apertar botao de volta ele voltar para o fragment anterior
		fragmentTransaction.commit();
	}

	public void onAqui(View view) {//teste para saber se está salvando elemento
		Intent intent = new Intent(this, ItemListActivity.class);
		startActivity(intent);
	}

	private Authorization _authorization;
	CoordinatorLayout _constraintLayout;
	private String _userId;
	private String _token;
}
