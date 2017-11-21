package com.example.luisafarias.myapplication.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.luisafarias.myapplication.R;
import com.example.luisafarias.myapplication.activities.MainActivity;
import com.example.luisafarias.myapplication.adapters.RssListRecyclerViewAdapter;
import com.example.luisafarias.myapplication.model.Rss;
import com.example.luisafarias.myapplication.model.RssListViewModel;
import com.example.luisafarias.myapplication.model.RssRepository;
import com.example.luisafarias.myapplication.util.Constants;
import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.auth.TokenAuthorization;
import java.util.ArrayList;
import java.util.List;

public class RssListFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			_token = getArguments().getString(Constants.TOKEN_KEY);
			// _rss = getArguments().getParcelable(Constants.RSS);
			if (_token != null) {
				_authorization = new TokenAuthorization(_token);
			}
		}
	}

	@SuppressLint("ResourceAsColor")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		List<Rss> rssList = new ArrayList();

		_view = inflater.inflate(R.layout.fragment_rss_list, container, false);
		RssListViewModel rssListViewModel =
			ViewModelProviders.of((FragmentActivity) getActivity()).
				get(RssListViewModel.class);//por algum motivo n está funcionando os outros, depois ver o porque
		_swipeRLayout = _view.findViewById(R.id.swiperefresh);
		_recycleView = _view.findViewById(R.id.recyclerView);
		_recycleViewAdapter =
			new RssListRecyclerViewAdapter(_view.getContext(), rssList, _token);
		LinearLayoutManager lm = new LinearLayoutManager(getActivity());
		_recycleView.setLayoutManager(lm);
		_recycleView.setAdapter(_recycleViewAdapter);

		reloadFeeds();
		_swipeRLayout.setOnRefreshListener(
			new SwipeRefreshLayout.OnRefreshListener() {
				@Override
				public void onRefresh() {
					refresh();
				}
			});
		_swipeRLayout.setColorSchemeColors(android.R.color.holo_blue_light,
			android.R.color.holo_orange_light);
		return _view;//nao funciona ainda as cores
	}

	public void reloadFeeds() {

		RssRepository.getInstance()
			.rssListAll(_authorization, new RssRepository.CallbackRssList() {
				@Override
				public void onSuccess(List<Rss> feedList) {
					_recycleViewAdapter.updateAnswers(feedList);

					_swipeRLayout.setRefreshing(false);
				}

				@Override
				public void onFailure(Exception e) {
					_swipeRLayout.setRefreshing(false);

					Log.e(MainActivity.class.getName(), e.getMessage());
				}
			});
	}

	public void refresh() {
		reloadFeeds();
	}

	private Authorization _authorization;
	private RssListRecyclerViewAdapter _recycleViewAdapter;
	private RecyclerView _recycleView;
	private SwipeRefreshLayout _swipeRLayout;
	private String _token;
	private View _view;
}
