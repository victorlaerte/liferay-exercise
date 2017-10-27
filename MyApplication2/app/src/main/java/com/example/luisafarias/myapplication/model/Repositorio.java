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
	Rss feed;
	ArrayList<Rss> feedList = new ArrayList();

	private Repositorio() {
	}

	public static Repositorio getInstance() {
		if (_uniqueInstance == null) {
			_uniqueInstance = new Repositorio();
		}
		return _uniqueInstance;
	}

	@Override
	public void addFeed(Rss feed, Authorization authorization,
						final CallbackFeed callbackFeed) throws JSONException {
		String userId = feed.getUserId();
		String url = feed.getUrl();
		String channelTitle = feed.getChannel().getTitle();

		if (feed != null) {

			JSONObject feedJsonObject = new JSONObject()
				.put("userId", userId)
				.put("url", url)
				.put("channelTitle", channelTitle);

			_weDeploy.data(Constants.DATA_URL)
				.authorization(authorization)
				.create("Feeds", feedJsonObject)
				.execute(new Callback() {
					public void onSuccess(Response response) {
						Log.d("repositorio", "salvo com sucesso");

						try {
							JSONObject jsonBody =
								new JSONObject(response.getBody());

							Rss feed = new Rss();
							feed.setUrl(jsonBody.getString("url"));
							feed.setId(jsonBody.getString("id"));
							Channel channel = new Channel();
							channel.setTitle(jsonBody.getString("channelTitle"));
							feed.setChannel(channel);

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
	public void updateFeed(Rss feed, Authorization authorization,//pq eu editaria um rss?
						   final CallbackFeed callbackFeed) throws JSONException {
		String url = feed.getUrl();

		if (feed != null) {

			JSONObject feedJsonObject =
				new JSONObject().put("url", url);

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

	public void removeFeed(final Rss feed, Authorization authorization,
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
	public Rss getFeed(Rss feed, Authorization authorization) {
		return null;
	}

	@Override
	public ArrayList<Rss> getAllFeeds(Authorization authorization) {

		_weDeploy.data(Constants.DATA_URL)
			.authorization(authorization)
			.get("Feeds")
			.execute(new Callback() {
				public void onSuccess(Response response) {

					try {
						JSONArray jsonArray = new JSONArray(response.getBody());

						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonBody = (JSONObject) jsonArray.get(i);
							//String nome = jsonBody.getString("name");
							String url = jsonBody.getString("url");
							String userId = jsonBody.getString("userId");
							String id = jsonBody.getString("id");
							String channelTitle = jsonBody.getString("channelTitle");
							Log.d("tittleChannel",channelTitle);
							Channel channel = new Channel();
							channel.setTitle(channelTitle);
							feed = new Rss(url, userId, null);
							feed.setId(id);
							feedList.add(feed);
							//String jsonBodyString = jsonBody.toString();
						}
					} catch (JSONException e) {
						Log.e(Repositorio.class.getName(),e.getMessage());
					}
				}

				public void onFailure(Exception e) {
					Log.e(MainActivity.class.getName(), e.getMessage());
				}
			});
		return feedList;
	}

	@Override
	public List<Rss> feedList() {
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

						List<Rss> listaFeed = new ArrayList<Rss>();

						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonBody = (JSONObject) jsonArray.get(i);
							Rss feed = new Rss();
							//feed.setTitle(jsonBody.getString("name"));
							feed.setUrl(jsonBody.getString("url"));
							//String a = feed.getUrl();
							feed.setId(jsonBody.getString("id"));
							//jsonBody.getString("channelTitle");
							//Log.d("repositorio",a);
							Channel temporaryChannel = new Channel();
							temporaryChannel.setTitle(jsonBody.getString("channelTitle"));
							feed.setChannel(temporaryChannel);
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
		void onSuccess(List<Rss> feedList);

		void onFailure(Exception e);
	}

	public interface CallbackFeed {
		void onSuccess(Rss feed);

		void onFailure(Exception e);
	}
}
