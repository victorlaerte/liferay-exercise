package com.example.luisafarias.myapplication.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
            if (_token != null) {
                _authorization = new TokenAuthorization(_token);
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        List<Rss> rssList = new ArrayList();

        _view = inflater.inflate(R.layout.fragment_rss_list, container, false);

        _rssListViewModel = ViewModelProviders.
                of((FragmentActivity) getActivity()).
                get(RssListViewModel.class);
        if (_rssListViewModel.getRssList() != null) {

            settingRecycleView(_rssListViewModel.getRssList());
            _recycleViewAdapter.setRssListAux(_rssListViewModel.getRssList());
            Log.d("RssFragment", "_rssListViewModel n é nulo");

        } else {
            settingRecycleView(rssList);
            reloadFeeds();

        }

        settingSwipeRLayout();

        ((MainActivity) getActivity()).showButton();

        return _view;
    }

    @SuppressLint("ResourceAsColor")
    private void settingSwipeRLayout() {
        _swipeRLayout = _view.findViewById(R.id.swiperefresh);

        _swipeRLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        reloadFeeds();
                    }
                });
        _swipeRLayout.setColorSchemeColors(android.R.color.holo_blue_light,
                android.R.color.holo_orange_light);//nao funciona ainda as cores
    }

    private void settingRecycleView(List<Rss> rssList) {
        _recycleView = _view.findViewById(R.id.recyclerView);
        _recycleViewAdapter =
                new RssListRecyclerViewAdapter(_view.getContext(), rssList, _token);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        _recycleView.setLayoutManager(lm);
        _recycleView.setAdapter(_recycleViewAdapter);
    }

    public void reloadFeeds() {

        RssRepository.getInstance()
                .rssListAll(_authorization, new RssRepository.CallbackRssList() {
                    @Override
                    public void onSuccess(List<Rss> feedList) {
                        _rssListViewModel.setRssList(feedList);
                        _recycleViewAdapter.setRssListAux(feedList);
                        _recycleViewAdapter.updateAnswers(_rssListViewModel.getRssList());

                        _swipeRLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        _swipeRLayout.setRefreshing(false);

                        Log.e(MainActivity.class.getName(), e.getMessage());
                    }
                });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_rss, menu);
        super.onCreateOptionsMenu(menu, inflater);
        _menuItem = menu.findItem(R.id.search);
        _searchView = (SearchView) _menuItem.getActionView();
        _searchView.setQueryHint("Buscar");

        if (!_rssListViewModel.getSearchText().isEmpty()) {
            _menuItem.expandActionView();
            _searchView.setQuery(_rssListViewModel.getSearchText(), true);//entender o booleano
            _recycleViewAdapter.getFilter().filter(_rssListViewModel.getSearchText());

            _searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.d("onCreateOptionsMenu", newText);
                    _recycleViewAdapter.getFilter().filter(newText);
                    _rssListViewModel.setSearchText(newText);

                    return false;
                }
            });
        } else {
            _menuItem.collapseActionView();
        }
    }


    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        Log.d("RssListFragment", "onOptionsItemSelected");

        int id = item.getItemId();
        switch (id) {
            case R.id.search:
                _searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        Log.d("RssListFragment", newText);
                        _recycleViewAdapter.getFilter().filter(newText);
                        _rssListViewModel.setSearchText(newText);

                        return false;
                    }
                });

                return true;

        }

        return super.onOptionsItemSelected(item);
    }


    private Authorization _authorization;
    private MenuItem _menuItem;
    private RssListRecyclerViewAdapter _recycleViewAdapter;
    private RecyclerView _recycleView;
    RssListViewModel _rssListViewModel;
    private SearchView _searchView;
    private SwipeRefreshLayout _swipeRLayout;
    private String _token;
    private View _view;
}
