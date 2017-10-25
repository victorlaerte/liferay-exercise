package com.example.luisafarias.myapplication.model;

import android.util.Log;
import com.example.luisafarias.myapplication.MainActivity;
import com.example.luisafarias.myapplication.interfaces.IRepositorio;
import com.example.luisafarias.myapplication.util.Constants;
import com.wedeploy.android.Callback;
import com.wedeploy.android.WeDeploy;
import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.transport.Response;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by luisafarias on 26/09/17.
 */

public class Repositorio implements IRepositorio {

	private static Repositorio _uniqueInstance;
	private WeDeploy _weDeploy = new WeDeploy.Builder().build();
	Feed feed;
	ArrayList<Feed> feedList = new ArrayList();
	//ArrayList<String> nomeLista = new ArrayList();

	private Repositorio() {
	}

	public static Repositorio getInstance() {
		if (_uniqueInstance == null) {
			_uniqueInstance = new Repositorio();
		}
		return _uniqueInstance;
	}

	@Override
	public void addFeed(Feed feed, Authorization authorization,
		final CallbackFeed callbackFeed) throws JSONException {
		String nomeUrl = feed.getTitle();
		String userId = feed.getUserId();
		String url = feed.getUrl();

		if (feed != null) {

			//            if(!feedList.contains(feed)){
			//                feedList.add(feed);
			//            }
			JSONObject feedJsonObject = new JSONObject().put("name", nomeUrl)
				.put("userId", userId)
				.put("url", url);
			//.put("id","teste12");

			_weDeploy.data(Constants.DATA_URL)
				.authorization(authorization)
				.create("Feeds", feedJsonObject)
				.execute(new Callback() {
					public void onSuccess(Response response) {
						Log.d("repositorio", "salvo com sucesso");

						try {
							JSONObject jsonBody =
								new JSONObject(response.getBody());

							Feed feed = new Feed();
							feed.setTitle(jsonBody.getString("name"));
							feed.setUrl(jsonBody.getString("url"));
							feed.setId(jsonBody.getString("id"));

							callbackFeed.onSuccess(feed);
						} catch (Exception e) {
							callbackFeed.onFailure(e);
						}
					}

					public void onFailure(Exception e) {
						Log.e("repositorio", e.getMessage());
						callbackFeed.onFailure(e);
					}
				});
		}
	}

	@Override
	public void updateFeed(Feed feed, Authorization authorization,
		final CallbackFeed callbackFeed) throws JSONException {
		String nomeUrl = feed.getTitle();
		String url = feed.getUrl();

		if (feed != null) {

			JSONObject feedJsonObject =
				new JSONObject().put("name", nomeUrl).put("url", url);

			_weDeploy.data(Constants.DATA_URL)
				.authorization(authorization)
				.update("Feeds/" + feed.getId(), feedJsonObject)
				.execute(new Callback() {
					public void onSuccess(Response response) {

						Log.d("repositorio", "editado com sucesso");
					}

					public void onFailure(Exception e) {
						Log.e("repositorio", e.getMessage());
					}
				});
		}
	}

	public void removeFeed(final Feed feed, Authorization authorization,
		final CallbackFeed callbackFeed) {

		if (feed != null) {
			String id = feed.getId();
			Log.d(Repositorio.class.getName(), id);

			_weDeploy.data(Constants.DATA_URL)
				.authorization(authorization)
				.delete("Feeds/" + id)
				.execute(new Callback() {
					@Override
					public void onSuccess(Response response) {
						Log.d(Repositorio.class.getName(), "removeu");
						feedList.remove(feed);
					}

					@Override
					public void onFailure(Exception e) {

						Log.e(Repositorio.class.getName(), e.getMessage());
					}
				});
		}
	}

	@Override
	public Feed getFeed(Feed feed, Authorization authorization) {
		return null;
	}

	@Override
	public ArrayList<Feed> getAllFeeds(Authorization authorization) {

		_weDeploy.data(Constants.DATA_URL)
			.authorization(authorization)
			.get("Feeds")
			.execute(new Callback() {
				public void onSuccess(Response response) {

					try {
						JSONArray jsonArray = new JSONArray(response.getBody());

						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonBody = (JSONObject) jsonArray.get(i);
							String nome = jsonBody.getString("name");
							String url = jsonBody.getString("url");
							String userId = jsonBody.getString("userId");
							String id = jsonBody.getString("id");
							feed = new Feed(nome, url, userId, null);
							feed.setId(id);
							feedList.add(feed);
							//String jsonBodyString = jsonBody.toString();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

				public void onFailure(Exception e) {
					Log.e(MainActivity.class.getName(), e.getMessage());
				}
			});
		return feedList;
	}

	@Override
	public List<Feed> feedList() {
		return null;
	}

	public void feedListAll(Authorization authorization,
		final CallbackFeeds callbackFeeds) {
		_weDeploy.data(Constants.DATA_URL)
			.authorization(authorization)
			.get("Feeds")
			.execute(new Callback() {
				@Override
				public void onSuccess(Response response) {
					//ArrayList<String> listaNomes = new ArrayList<String>();
					try {
						JSONArray jsonArray = new JSONArray(response.getBody());

						List<Feed> listaFeed = new ArrayList<Feed>();

						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonBody = (JSONObject) jsonArray.get(i);
							Feed feed = new Feed();
							feed.setTitle(jsonBody.getString("name"));
							feed.setUrl(jsonBody.getString("url"));
							feed.setId(jsonBody.getString("id"));
							listaFeed.add(feed);
						}

						callbackFeeds.onSuccess(listaFeed);
					} catch (Exception e) {
						callbackFeeds.onFailure(e);
					}
				}

				@Override
				public void onFailure(Exception e) {
					callbackFeeds.onFailure(e);
				}
			});
	}

	public interface CallbackFeeds {
		void onSuccess(List<Feed> feedList);

		void onFailure(Exception e);
	}

	public interface CallbackFeed {
		void onSuccess(Feed feed);

		void onFailure(Exception e);
	}
}
