package com.example.luisafarias.myapplication.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import com.example.luisafarias.myapplication.R;
import com.example.luisafarias.myapplication.model.Rss;
import com.example.luisafarias.myapplication.model.RssListViewModel;
import com.example.luisafarias.myapplication.model.RssModel;
import com.example.luisafarias.myapplication.model.RssRepository;
import com.example.luisafarias.myapplication.util.Constants;
import com.wedeploy.android.Callback;
import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.auth.TokenAuthorization;
import com.wedeploy.android.transport.Response;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by luisafarias on 10/10/17.
 */

public class PopUpFragment extends DialogFragment {

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		this._rss = getArguments().getParcelable(Constants.RSS);

		String nome = _rss.getChannel().getTitle();
		String token = getArguments().getString(Constants.TOKEN);
		_authorization = new TokenAuthorization(token);

		_rssListViewModel = ViewModelProviders.
			of(getActivity()).get(RssListViewModel.class);
		Log.d(CLASS_NAME, _rssListViewModel.getRssList().toString());

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		_realm = Realm.getDefaultInstance();

		builder.setMessage(getString(R.string.deseja_excluir) + nome + "?")
			.setPositiveButton(R.string.excluir,
				(dialog, which) -> deleteRss(_rssListViewModel))
			.setNegativeButton(R.string.cancelar, (dialog, which) -> {
			});

		return builder.create();
	}

	private void deleteRss(final RssListViewModel rssListViewModel) {
		if (_rss != null && _authorization != null) {
			RssRepository.getInstance()
				.removeRss(_rss, _authorization, new Callback() {
					@Override
					public void onSuccess(Response response) {

						rssListViewModel.deleteRss(_rss);

						deleteRealmRss();

						Log.d(CLASS_NAME,
							getString(R.string.excluido_com_sucesso));
					}

					@Override
					public void onFailure(Exception e) {
						Log.e(CLASS_NAME, e.getMessage());
					}
				});
		}
	}

	private void deleteRealmRss() {
		_realm.executeTransaction(realm -> {
			_rssResults = _realm.where(RssModel.class).
				equalTo(Constants._ID, _rss.getId()).findAll();

			_rssResults.deleteAllFromRealm();
		});
	}

	@Override
	public void onPause() {
		FragmentManager fm = (getActivity()).getFragmentManager();
		RssListFragment fld = (RssListFragment) fm.findFragmentByTag(
			Constants.GET_RSS_LIST_FRAGMENT);
		fld.reloadFeeds();
		super.onPause();
	}

	final private String CLASS_NAME = "PopUpFragment";
	private Authorization _authorization;
	private Realm _realm;
	private RealmResults<RssModel> _rssResults;
	private Rss _rss;
	private RssListViewModel _rssListViewModel;
}
