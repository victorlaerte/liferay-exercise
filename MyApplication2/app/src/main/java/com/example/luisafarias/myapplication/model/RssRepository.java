package com.example.luisafarias.myapplication.model;

import android.util.Log;
import com.example.luisafarias.myapplication.R;
import com.example.luisafarias.myapplication.activities.MainActivity;
import com.example.luisafarias.myapplication.interfaces.WeRetrofitService;
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
import retrofit2.Call;

/**
 * Created by luisafarias on 26/09/17.
 */

public class RssRepository {

	private static RssRepository _uniqueInstance;
	private WeDeploy _weDeploy = new WeDeploy.Builder().build();
	Rss rss;
	ArrayList<Rss> rssList = new ArrayList();

	private RssRepository() {
	}

	public static RssRepository getInstance() {
		if (_uniqueInstance == null) {
			_uniqueInstance = new RssRepository();
		}
		return _uniqueInstance;
	}

	public void addRss(final Rss rss, Authorization authorization,
		final Callback callback) throws JSONException {

		if (rss != null) {
			String userId = rss.getUserId();
			String url = rss.getUrl();
			String channelTitle = rss.getChannel().getTitle();
			String imageUrl = rss.getChannel().getImage().getUrl();

			JSONObject feedJsonObject =
				new JSONObject().put(Constants.USER_ID, userId)
					.put(Constants.URL, url)
					.put(Constants.CHANNEL_TITLE, channelTitle)
					.put(Constants.IMAGE_URL, imageUrl);

			_weDeploy.data(Constants.DATA_URL)
				.authorization(authorization)
				.create(Constants.FEEDS, feedJsonObject)
				.execute(new Callback() {
					@Override
					public void onSuccess(Response response) {
						try {
							JSONObject jsonBody =
								new JSONObject(response.getBody());

							rss.setUrl(jsonBody.getString(Constants.URL));
							rss.setId(jsonBody.getString(Constants.ID));
							Channel channel = new Channel();
							channel.setTitle(
								jsonBody.getString(Constants.CHANNEL_TITLE));
							Image image = new Image();
							image.setUrl(jsonBody.getString(Constants.IMAGE_URL));
							channel.setImage(image);
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
		}
	}

	public void removeRss(final Rss rss, Authorization authorization,
		final Callback callback) {

		if (rss != null) {
			String id = rss.getId();
			Log.d(RssRepository.class.getName(), id);

			_weDeploy.data(Constants.DATA_URL)
				.authorization(authorization)
				.delete(Constants.FEEDS + "/" + id)
				.execute(new Callback() {
					@Override
					public void onSuccess(Response response) {
						rssList.remove(rss);
						callback.onSuccess(response);
					}

					@Override
					public void onFailure(Exception e) {

						Log.e(RssRepository.class.getName(), e.getMessage());
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
			.get(Constants.RSS_RSS)
			.execute(new Callback() {
				public void onSuccess(Response response) {

					try {
						JSONArray jsonArray = new JSONArray(response.getBody());

						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonBody = (JSONObject) jsonArray.get(i);
							String url = jsonBody.getString(Constants.URL);
							String userId =
								jsonBody.getString(Constants.USER_ID);
							String id = jsonBody.getString(Constants.ID);
							String channelTitle =
								jsonBody.getString(Constants.CHANNEL_TITLE);
							Channel channel = new Channel();
							channel.setTitle(channelTitle);
							rss = new Rss(url, userId, null);
							rss.setId(id);
							rssList.add(rss);
						}
					} catch (JSONException e) {
						Log.e(RssRepository.class.getName(), e.getMessage());
					}
				}

				public void onFailure(Exception e) {

					Log.e(MainActivity.class.getName(), e.getMessage());
				}
			});
		return rssList;
	}

	public void rssListAll(Authorization authorization,
		/**nesse caso em especial eu acho melhor deixar o callback extra pq eu uso esse metodo no RssListFragment onde preciso **/
		final CallbackRssList callbackRssList) {
		_weDeploy.data(Constants.DATA_URL)
			.authorization(authorization)
			.get(Constants.FEEDS)
			.execute(new Callback() {
				@Override
				public void onSuccess(Response response) {
					try {
						JSONArray jsonArray = new JSONArray(response.getBody());

						List<Rss> listaRss = new ArrayList();

						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonBody = (JSONObject) jsonArray.get(i);
							Rss rss = new Rss();
							rss.setUrl(jsonBody.getString(Constants.URL));
							rss.setId(jsonBody.getString(Constants.ID));
							Channel temporaryChannel = new Channel();
							temporaryChannel.setTitle(
								jsonBody.getString(Constants.CHANNEL_TITLE));
							Image temporaryImage = new Image();
							temporaryImage.setUrl(jsonBody.getString(Constants.IMAGE_URL));
							temporaryChannel.setImage(temporaryImage);
							rss.setChannel(temporaryChannel);
							Log.d(CLASS_NAME, rss.getUrl());
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

	public void getRemoteChannel(Rss rss,
		final CallbackChannel callbackChannel) {
		WeRetrofitService wrs = RetrofitClient.getInstance(rss.getURLHost())
			.create(WeRetrofitService.class);

		wrs.getItems(rss.getURLEndPoint())
			.enqueue(new retrofit2.Callback<Rss>() {
				@Override
				public void onResponse(Call<Rss> call,
					retrofit2.Response<Rss> response) {

					if (response.isSuccessful()) {
						Channel channel = response.body().getChannel();
						Image image = response.body().getChannel().getImage();
						channel.setImage(image);
						try {
							callbackChannel.onSuccess(channel);
						} catch (JSONException e) {
							e.printStackTrace();
							Log.d(CLASS_NAME, e.getMessage());
						}
					}
				}

				@Override
				public void onFailure(Call<Rss> call, Throwable t) {
					Log.e(CLASS_NAME, t.getMessage());
					callbackChannel.onFailure(t);
				}
			});
	}

	public interface CallbackChannel {
		void onSuccess(Channel channel) throws JSONException;

		void onFailure(Throwable t);
	}

	public interface CallbackRssList {
		void onSuccess(List<Rss> rssList);

		void onFailure(Exception e);
	}

	final private String CLASS_NAME = "RssRepository";
}
