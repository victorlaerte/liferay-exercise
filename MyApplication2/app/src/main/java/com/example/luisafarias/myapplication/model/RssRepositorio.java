package com.example.luisafarias.myapplication.model;

import android.util.Log;
import com.example.luisafarias.myapplication.activities.MainActivity;
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

public class RssRepositorio {

	private static RssRepositorio _uniqueInstance;
	private WeDeploy _weDeploy = new WeDeploy.Builder().build();
	Rss rss;
	ArrayList<Rss> rssList = new ArrayList();

	private RssRepositorio() {
	}

	public static RssRepositorio getInstance() {
		if (_uniqueInstance == null) {
			_uniqueInstance = new RssRepositorio();
		}
		return _uniqueInstance;
	}


	public void addRss(Rss rss, Authorization authorization,
					   final Callback callback) throws JSONException {
		String userId = rss.getUserId();
		String url = rss.getUrl();
		String channelTitle = rss.getChannel().getTitle();

		if (rss != null) {

			JSONObject feedJsonObject = new JSONObject()
				.put("userId", userId)
				.put("url", url)
				.put("channelTitle", channelTitle);

			_weDeploy.data(Constants.DATA_URL)
					.authorization(authorization)
					.create("Feeds", feedJsonObject)
					.execute(new Callback() {
						@Override
						public void onSuccess(Response response) {
							Log.d("repositorio", "salvo com sucesso");

							try {
								JSONObject jsonBody =
										new JSONObject(response.getBody());

								Rss rss = new Rss();
								rss.setUrl(jsonBody.getString("url"));
								rss.setId(jsonBody.getString("id"));
								Channel channel = new Channel();
								channel.setTitle(jsonBody.getString("channelTitle"));
								rss.setChannel(channel);
							} catch (Exception e) {
								callback.onFailure(e);
							}

							callback.onSuccess(response);
						}

						@Override
						public void onFailure(Exception e) {

							callback.onFailure(e);
						}
					});

//			_weDeploy.data(Constants.DATA_URL)
//				.authorization(authorization)
//				.create("Feeds", feedJsonObject)
//				.execute(new Callback() {
//					public void onSuccess(Response response) {
//						Log.d("repositorio", "salvo com sucesso");
//
//						try {
//							JSONObject jsonBody =
//								new JSONObject(response.getBody());
//
//							Rss rss = new Rss();
//							rss.setUrl(jsonBody.getString("url"));
//							rss.setId(jsonBody.getString("id"));
//							Channel channel = new Channel();
//							channel.setTitle(jsonBody.getString("channelTitle"));
//							rss.setChannel(channel);
//
//							callbackRss.onSuccess(rss);
//						} catch (Exception e) {
//							callbackRss.onFailure(e);
//						}
//					}
//
//					public void onFailure(Exception e) {
//						Log.e("repositorio", e.getMessage());
//						callbackRss.onFailure(e);
//					}
//				});
		}
	}

//	@Override
//	public void updateRss(Rss rss, Authorization authorization,//pq eu editaria um rss?
//						   final Callback callback) throws JSONException {
//		String url = rss.getUrl();
//
//		if (rss != null) {
//
//			JSONObject feedJsonObject =
//				new JSONObject().put("url", url);
//
//			_weDeploy.data(Constants.DATA_URL)
//				.authorization(authorization)
//				.update("rss/" + rss.getId(), feedJsonObject)
//				.execute(new Callback() {
//					public void onSuccess(Response response) {
//
//						Log.d("repositorio", "editado com sucesso");
//						callback.onSuccess(response);
//					}
//
//					public void onFailure(Exception e) {
//
//						Log.e("repositorio", e.getMessage());
//						callback.onFailure(e);
//					}
//				});
//		}
//	}

	public void removeRss(final Rss rss, Authorization authorization,
						   final Callback callback) {

		if (rss != null) {
			String id = rss.getId();
			Log.d(RssRepositorio.class.getName(), id);

			_weDeploy.data(Constants.DATA_URL)
				.authorization(authorization)
				.delete("Feeds/" + id)
				.execute(new Callback() {
					@Override
					public void onSuccess(Response response) {
						Log.d(RssRepositorio.class.getName(), "removeu");
						rssList.remove(rss);
						callback.onSuccess(response);
					}

					@Override
					public void onFailure(Exception e) {

						Log.e(RssRepositorio.class.getName(), e.getMessage());
						callback.onFailure(e);
					}
				});
		}
	}


	public Rss getRss(Rss rss, Authorization authorization) {
		return null;
	}


	public ArrayList<Rss> getAllRss(Authorization authorization) {

		_weDeploy.data(Constants.DATA_URL)
			.authorization(authorization)
			.get("Rss")
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
							rss = new Rss(url, userId, null);
							rss.setId(id);
							rssList.add(rss);
							//String jsonBodyString = jsonBody.toString();
						}
					} catch (JSONException e) {
						Log.e(RssRepositorio.class.getName(),e.getMessage());
					}
				}

				public void onFailure(Exception e) {

					Log.e(MainActivity.class.getName(), e.getMessage());
				}
			});
		return rssList;
	}


	public List<Rss> rssList() {
		return null;
	}

	public void rssListAll(Authorization authorization,/**nesse caso em especial eu acho melhor deicar o callback extra pq eu uso esse metodo no RssListFragment onde preciso **/
		final CallbackRssList callbackRssList) {
		_weDeploy.data(Constants.DATA_URL)
			.authorization(authorization)
			.get("Feeds")
			.execute(new Callback() {
				@Override
				public void onSuccess(Response response) {
					//ArrayList<String> listaNomes = new ArrayList<String>();
					try {
						JSONArray jsonArray = new JSONArray(response.getBody());

						List<Rss> listaRss = new ArrayList<Rss>();

						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonBody = (JSONObject) jsonArray.get(i);
							Rss rss = new Rss();
							//feed.setTitle(jsonBody.getString("name"));
							rss.setUrl(jsonBody.getString("url"));
							//String a = feed.getUrl();
							rss.setId(jsonBody.getString("id"));
							//jsonBody.getString("channelTitle");
							//Log.d("repositorio",a);
							Channel temporaryChannel = new Channel();
							temporaryChannel.setTitle(jsonBody.getString("channelTitle"));
							rss.setChannel(temporaryChannel);
							Log.d("RssRepositorio", rss.getUrl());
							listaRss.add(rss);
						}

						callbackRssList.onSuccess(listaRss);
					} catch (Exception e) {
						callbackRssList.onFailure(e);
					}
				}

				@Override
				public void onFailure(Exception e) {
					callbackRssList.onFailure(e);
				}
			});
	}

	public interface CallbackRssList {
		void onSuccess(List<Rss> rssList);

		void onFailure(Exception e);
	}

}
