package com.example.luisafarias.myapplication.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.luisafarias.myapplication.R;
import com.example.luisafarias.myapplication.fragments.RssListFragment;
import com.example.luisafarias.myapplication.fragments.NewRssFragment;
import com.example.luisafarias.myapplication.model.Rss;
import com.example.luisafarias.myapplication.model.Repositorio;
import com.example.luisafarias.myapplication.util.Constants;
import com.wedeploy.android.Callback;
import com.wedeploy.android.WeDeploy;
import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.auth.TokenAuthorization;
import com.wedeploy.android.transport.Response;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

	ConstraintLayout container;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		container = (ConstraintLayout) findViewById(R.id.container);

		Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(myToolbar);

		RssListFragment feedListFragment = new RssListFragment();
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		//       ft.replace(R.id.frame_layout_fragment,feedListFragment, "test");
		ft.add(R.id.frame_layout_fragment, feedListFragment, "test");
		ft.commit();

		Bundle data = getIntent().getBundleExtra(Constants.TOKEN_USER_ID);
		_token = data.getString(Constants.TOKEN_KEY);
		_userId = data.getString(Constants.USER_ID);

		if (_userId != null) Log.d("mainrecebeuserID", _userId);

		Bundle bundle = new Bundle();
		bundle.putString(Constants.TOKEN_KEY, _token);
		feedListFragment.setArguments(bundle);

		if (_token == null) {
			throw new IllegalArgumentException();
		}

		_authorization = new TokenAuthorization(_token);
		//_allFeeds = (ListView) findViewById(R.id.lista_feed);

		//reloadFeeds();

		/**onClick get feed from newFeedFragment **/

//		Button actionSaveFeed = (Button) findViewById(R.id.saveFeed);
//		actionSaveFeed.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				//EditText newUrlName = (EditText) findViewById(R.id.newNameFeed);
//				EditText newUrl = (EditText) findViewById(R.id.newUrlFeed);
//
//				//String name = newUrlName.getText().toString();
//				String url = newUrl.getText().toString();
//
//				Rss feed = new Rss(url, _userId, null);
//				Log.d("mainActivity", feed.getUrl());
//				try {
//					saveFeed(feed);
//				} catch (JSONException e) {
//					Log.e(MainActivity.class.getName(), e.getMessage());
//				}
//			}
//		});
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_feed, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		final Intent intent = new Intent(this, LoginActivity.class);
		int id = item.getItemId();

		if (id == R.id.logout) {
			_weDeploy.auth(Constants.AUTH_URL)
				.authorization(_authorization)
				.signOut()
				.execute(new Callback() {
					public void onSuccess(Response response) {
						SharedPreferences sharedPref =
							getSharedPreferences(Constants.USER, MODE_PRIVATE);
						SharedPreferences.Editor editor = sharedPref.edit();
						editor.clear();
						editor.apply();
						Log.d(MainActivity.class.getName(), "saiu");
						finish();
						startActivity(intent);
					}

					public void onFailure(Exception e) {

						Log.e(MainActivity.class.getName(), e.getMessage());
					}
				});
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
		final Intent intent) {

		super.onActivityResult(requestCode, resultCode, intent);

		if (requestCode == ACCESS_RESULT_NEW_FEED) {
			if (intent != null) {
				Rss feed = intent.getExtras().getParcelable(Constants.RSS);
				try {
					Repositorio.getInstance()
						.addFeed(feed, _authorization,
							new Repositorio.CallbackFeed() {
								@Override
								public void onSuccess(Rss feed) {
									//reloadFeeds();
									Log.d(MainActivity.class.getName(),
										"salvou");
									Snackbar.make(container, "Salvou",
										Snackbar.LENGTH_LONG).show();
								}

								@Override
								public void onFailure(Exception e) {
									Log.e(MainActivity.class.getName(),
										e.getMessage());
									Snackbar.make(container, e.getMessage(),
										Snackbar.LENGTH_LONG).show();
								}
							});
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void goAddNewFeed(View view) {
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
		fragmentTransaction.addToBackStack(
			null);//quando apertar botao de voltar ele volta para o fragment anterior
		fragmentTransaction.commit();
	}

	public void goEditFeed(Rss feed) {
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

//	public void saveFeed(Rss feed) throws JSONException {
//		Repositorio.getInstance()
//			.addFeed(feed, _authorization, new Repositorio.CallbackFeed() {
//
//				@Override
//				public void onSuccess(Rss feed) {
//					Log.d(MainActivity.class.getName(), "salvou");
//					Snackbar.make(container, "Salvou", Snackbar.LENGTH_LONG)
//						.show();
//				}
//
//				@Override
//				public void onFailure(Exception e) {
//					Log.e(MainActivity.class.getName(), e.getMessage());
//					Snackbar.make(container, e.getMessage(),
//						Snackbar.LENGTH_LONG).show();
//				}
//			});
//	}

	public void onAqui(View view) {//teste para saber se está salvando elemento

		Intent intent = new Intent(this, ItemListActivity.class);
		startActivity(intent);
	}

	//private ListView _allFeeds;
	private Authorization _authorization;
	//private Rss _feed;
	private WeDeploy _weDeploy = new WeDeploy.Builder().build();
	private String _userId;
	private String _token;
	//private FeedListAdapter _feedAdapter;
	private final int ACCESS_RESULT_NEW_FEED = 1234;
}
