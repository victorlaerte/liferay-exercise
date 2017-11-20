package com.example.luisafarias.myapplication.fragments;

import android.app.Fragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.luisafarias.myapplication.R;
import com.example.luisafarias.myapplication.interfaces.WeRetrofitService;
import com.example.luisafarias.myapplication.model.Channel;
import com.example.luisafarias.myapplication.model.Rss;
import com.example.luisafarias.myapplication.model.RssRepositorio;
import com.example.luisafarias.myapplication.model.RetrofitClient;
import com.example.luisafarias.myapplication.util.Constants;
import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.auth.TokenAuthorization;

import org.json.JSONException;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewRssFragment extends Fragment {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments() != null) {
			_userId = getArguments().getString(Constants.USER_ID);
			_token = getArguments().getString(Constants.TOKEN);
			//_rss = getArguments().getParcelable(Constants.RSS);
			_authorization = new TokenAuthorization(_token);
			//Log.d("NewRssFragment", "testando");
		}
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		_view = inflater.inflate(R.layout.fragment_new_rss, container, false);
		ClipboardManager cbm = (ClipboardManager) _view.getContext()
				.getSystemService(Context.CLIPBOARD_SERVICE);
		ClipData cd = cbm.getPrimaryClip();
		ClipData.Item item = cd.getItemAt(0);
		final String copied = item.getText().toString();

		_urlEditText = _view.findViewById(R.id.newUrlFeed);

		if (android.webkit.URLUtil.isValidUrl(copied)){
			_urlEditText.setText(copied);
		}

		Button save = _view.findViewById(R.id.save);

			/**NewFeed**/
			save.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

						String url = _urlEditText.getText().toString();
						Rss rss = new Rss(url, _userId, null);
						Log.d("teste", url);
						try {
							if (android.webkit.URLUtil.isValidUrl(url)){
								saveNewRss(rss);
							}else {
								Snackbar.make(v.getRootView()
										.findViewById(R.id.coordinator),"Url invalida",
										Snackbar.LENGTH_LONG).show();
							}
						} catch (IOException e) {
							e.printStackTrace();
							Log.d("NewRssFragment",e.getMessage());
							Snackbar.make(v.getRootView()
											.findViewById(R.id.coordinator),e.getMessage(),
									Snackbar.LENGTH_LONG).show();
						}

				}
			});

		return _view;
	}

	public void saveNewRss(final Rss rss) throws IOException {
		RssRepositorio.getInstance().getRemoteChannel(rss, new RssRepositorio.CallBackChannel() {
			@Override
			public void onSuccess(Channel channel) throws JSONException {
				rss.setChannel(channel);
				RssRepositorio.getInstance().addRss(rss, _authorization,
						new com.wedeploy.android.Callback() {
					@Override
					public void onSuccess(com.wedeploy.android.transport.Response response) {
						Log.d("NewRssFragment", "Salvo com sucesso");
					}

					@Override
					public void onFailure(Exception e) {
						Log.e("NewRssFragment", e.getMessage());
						Snackbar.make(_view.getRootView()
										.findViewById(R.id.coordinator),e.getMessage(),
								Snackbar.LENGTH_LONG).show();
					}
				});
			}

			@Override
			public void onFailure(Throwable t) {
				Log.e("NewRssFragment", t.getMessage());
				Snackbar.make(_view.getRootView()
								.findViewById(R.id.coordinator),t.getMessage(),
						Snackbar.LENGTH_LONG).show();
			}
		});

	}

	private Authorization _authorization;
	private String _token;
	private String _userId;
	private View _view;
	private EditText _urlEditText;
}
