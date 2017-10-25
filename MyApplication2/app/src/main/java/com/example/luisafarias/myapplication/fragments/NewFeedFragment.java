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
import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.auth.TokenAuthorization;
import org.json.JSONException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewFeedFragment extends Fragment {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments() != null) {
			_userId = getArguments().getString("userId");
			_token = getArguments().getString("token");
			_feed = getArguments().getParcelable("feed");
			_newOrEdit = getArguments().getBoolean("newOredit");
			_authorization = new TokenAuthorization(_token);
			Log.d("NewFeedFragment", "testando");
		}
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		_view = inflater.inflate(R.layout.fragment_new_feed, container, false);
		_nome = _view.findViewById(R.id.newNameFeed);
		_url = _view.findViewById(R.id.newUrlFeed);
		Button save = _view.findViewById(R.id.save);
		if (_newOrEdit) {
			_nome.setText(_feed.getTitle());
			_url.setText(_feed.getUrl());

			save.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						updateFeed();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
		} else {
			save.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String name = _nome.getText().toString();
					String url = _url.getText().toString();
					Feed feed = new Feed(name, url, _userId, null);
					Log.d("teste", name + "  " + url);
					try {
						saveNewFeed(feed);
					} catch (JSONException e) {
						Log.e(NewFeedFragment.class.getName(), e.getMessage());
					}
				}
			});
		}
		return _view;
	}

	public void updateFeed() throws JSONException {
		Feed feed = _feed;
		feed.setTitle(_nome.getText().toString());
		feed.setUrl(_url.getText().toString());
		Repositorio.getInstance()
			.updateFeed(feed, _authorization, new Repositorio.CallbackFeed() {
				@Override
				public void onSuccess(Feed feed) {

				}

				@Override
				public void onFailure(Exception e) {

				}
			});
	}

	public void saveNewFeed(Feed parameter) throws JSONException {
		Repositorio.getInstance()
			.addFeed(parameter, _authorization, new Repositorio.CallbackFeed() {
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

	public Channel getChannelNF() {
		WeRetrofitService wrs = RetrofitClient.getInstance(_feed.getPartMain())
			.create(WeRetrofitService.class);
		wrs.getItems().enqueue(new Callback<Feed>() {
			@Override
			public void onResponse(Call<Feed> call, Response<Feed> response) {
				if (response.isSuccessful()) {
					_channel = response.body().getChannel();
				}
			}

			@Override
			public void onFailure(Call<Feed> call, Throwable t) {
				Log.e("NewFeedFragment", t.getMessage());
			}
		});
		return _channel;
	}

	private Authorization _authorization;
	private Channel _channel;
	private Feed _feed;
	private boolean _newOrEdit = false;
	private EditText _nome;
	private String _token;
	private String _userId;
	private View _view;
	private EditText _url;
}
