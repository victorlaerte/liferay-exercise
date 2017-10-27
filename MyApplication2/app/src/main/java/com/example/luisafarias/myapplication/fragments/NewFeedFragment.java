package com.example.luisafarias.myapplication.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.luisafarias.myapplication.R;
import com.example.luisafarias.myapplication.interfaces.WeRetrofitService;
import com.example.luisafarias.myapplication.model.Channel;
import com.example.luisafarias.myapplication.model.Feed;
import com.example.luisafarias.myapplication.model.Repositorio;
import com.example.luisafarias.myapplication.model.RetrofitClient;
import com.example.luisafarias.myapplication.util.Constants;
import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.auth.TokenAuthorization;
import org.json.JSONException;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewFeedFragment extends Fragment {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments() != null) {
			_userId = getArguments().getString(Constants.USER_ID);
			_token = getArguments().getString(Constants.TOKEN);
			_feed = getArguments().getParcelable(Constants.FEED);
			_newOrEdit = getArguments().getBoolean(Constants.NEW_OR_EDIT);
			_authorization = new TokenAuthorization(_token);
			Log.d("NewFeedFragment", "testando");
		}
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		_view = inflater.inflate(R.layout.fragment_new_feed, container, false);
		//_nome = _view.findViewById(R.id.newNameFeed);
		_url = _view.findViewById(R.id.newUrlFeed);
		Button save = _view.findViewById(R.id.save);
		if (_newOrEdit) { /**updateFeed**/
			//_nome.setText(_feed.getTitle());
			_url.setText(_feed.getUrl());

			save.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						updateFeed(_feed);
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		} else {
			/**NewFeed**/
			save.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//String name = _nome.getText().toString();
					String url = _url.getText().toString();
					Feed feed = new Feed(url, _userId, null);
					Log.d("teste", url);
					try {
						saveNewFeed(feed);//nesse metodo deve pegar o title do channel ou aqui dentro mesmo
					} catch (JSONException e) {
						Log.e(NewFeedFragment.class.getName(), e.getMessage());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
		return _view;
	}

	public void updateFeed(final Feed feed) throws JSONException, IOException {
		//aqui tbm o channel deve ser setado
		//feed.setTitle(_nome.getText().toString());
		getChannelNF(feed, new CallBackChannel() {
			@Override
			public void onSuccess(Channel channel) throws JSONException {

				feed.setUrl(_url.getText().toString());
				Repositorio.getInstance()
						.updateFeed(feed, _authorization, new Repositorio.CallbackFeed() {
							@Override
							public void onSuccess(Feed feed) {

							}

							@Override
							public void onFailure(Exception e) {
								Log.e("NewFeedFrament", e.getMessage());
							}
						});
			}

			@Override
			public void onFailure(Throwable t) {
				Log.e("NewFeedFrament", t.getMessage());
			}
		});

	}

	public void saveNewFeed(final Feed feed) throws JSONException, IOException {
		getChannelNF(feed, new CallBackChannel() {
			//Feed feedParameter = feed;
			@Override
			public void onSuccess(Channel channel) throws JSONException {
				String title = channel.getTitle();
				feed.setChannel(channel);
				Log.d("NewFeedFragment", "deu certo");
				Repositorio.getInstance()
						.addFeed(feed, _authorization, new Repositorio.CallbackFeed() {
							@Override
							public void onSuccess(Feed feed) {
								Log.d(NewFeedFragment.class.getName(), "salvo com sucesso");

							}

							@Override
							public void onFailure(Exception e) {

								Log.e(NewFeedFragment.class.getName(), e.getMessage());
							}
						});
			}

			@Override
			public void onFailure(Throwable t) {
				Log.e("NewFeedFrament", t.getMessage());
			}
		});

	}

	public void getChannelNF(Feed feed, final CallBackChannel callBackChannel) throws IOException {
		WeRetrofitService wrs = RetrofitClient.getInstance(feed.getPartMain())
			.create(WeRetrofitService.class);
		wrs.getItems(feed.getPartXml()).enqueue(new Callback<Feed>() {
			@Override
			public void onResponse(Call<Feed> call, Response<Feed> response) {
				if (response.isSuccessful()) {
					_channel = response.body().getChannel();
					try {
						callBackChannel.onSuccess(_channel);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			public void onFailure(Call<Feed> call, Throwable t) {

				Log.e("NewFeedFragment", t.getMessage());
				callBackChannel.onFailure(t);
			}
		});
	}

	public interface CallBackChannel{
		void onSuccess(Channel channel) throws JSONException;
		void onFailure(Throwable t);
	}

	private Authorization _authorization;
	private Channel _channel;
	private Feed _feed;
	private boolean _newOrEdit = false;
	//private EditText _nome;
	private String _token;
	private String _userId;
	private View _view;
	private EditText _url;
}
