package com.example.luisafarias.myapplication.fragments;

import android.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;

import com.example.luisafarias.myapplication.R;
import com.example.luisafarias.myapplication.activities.MainActivity;
import com.example.luisafarias.myapplication.model.Channel;
import com.example.luisafarias.myapplication.model.Rss;
import com.example.luisafarias.myapplication.model.RssListViewModel;
import com.example.luisafarias.myapplication.model.RssModel;
import com.example.luisafarias.myapplication.model.RssRepository;
import com.example.luisafarias.myapplication.util.Constants;
import com.wedeploy.android.Callback;
import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.auth.TokenAuthorization;
import com.wedeploy.android.transport.Response;

import java.io.IOException;

import org.json.JSONException;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class NewRssFragment extends Fragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            _userId = getArguments().getString(Constants.USER_ID);
            _token = getArguments().getString(Constants.TOKEN);
            _authorization = new TokenAuthorization(_token);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        _view = inflater.inflate(R.layout.fragment_new_rss, container, false);
        _urlEditText = _view.findViewById(R.id.newUrlFeed);

        _rssListViewModel = ViewModelProviders.of((FragmentActivity) getActivity()).get(RssListViewModel.class);

        final String copied = getClipboardString();

        if (URLUtil.isValidUrl(copied)) {
            _urlEditText.setText(copied);
        }

        Button save = _view.findViewById(R.id.save);

        _realm = Realm.getDefaultInstance();
        _rssResults = _realm.where(RssModel.class).findAll();


        /**NewFeed**/
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newRss(v);
            }
        });
        ((MainActivity) getActivity()).hideButton();

        return _view;
    }

    private void newRss(View v) {
        String url = _urlEditText.getText().toString();
        Rss rss = new Rss(url, _userId, null);

        Log.d(TAG, url);
        try {
            if (URLUtil.isValidUrl(url)) {
                saveNewRss(rss);
            } else {
                Snackbar.make(
                        v.getRootView().findViewById(R.id.frag_new_rss),
                        "Url invalida", Snackbar.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
            Snackbar.make(
                    v.getRootView().findViewById(R.id.frag_new_rss),
                    e.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @NonNull
    private String getClipboardString() {
        ClipboardManager cbm = (ClipboardManager) _view.getContext()
                .getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData cd = cbm.getPrimaryClip();
        ClipData.Item item = cd.getItemAt(0);
        return item.getText().toString();
    }

    public void saveNewRss(final Rss rss) throws IOException {
        RssRepository.getInstance()
                .getRemoteChannel(rss, new RssRepository.CallbackChannel() {

                    @Override
                    public void onSuccess(Channel channel) throws JSONException {
                        rss.setChannel(channel);
                        RssRepository.getInstance()
                                .addRss(rss, _authorization, new Callback() {
                                    @Override
                                    public void onSuccess(Response response) {
                                        _rssListViewModel.addRss(rss);

                                        addRssRealm(rss);

                                        Log.d(TAG, "Salvo com sucesso");
                                        Snackbar.make(
                                                _view.getRootView().findViewById(R.id.frag_new_rss),
                                                "Salvo com sucesso", Snackbar.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onFailure(Exception e) {
                                        Log.e(TAG, e.getMessage());
                                        Snackbar.make(
                                                _view.getRootView().findViewById(R.id.frag_new_rss),
                                                e.getMessage(), Snackbar.LENGTH_LONG).show();
                                    }
                                });
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e(TAG, t.getMessage());
                        Snackbar.make(
                                _view.getRootView().findViewById(R.id.frag_new_rss),
                                t.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                });
    }

    private void addRssRealm(Rss rss) {
        _realm.beginTransaction();
        RealmList<String> realmStringList = new RealmList<>();
        _rssModel = _realm.createObject(RssModel.class,rss.getId());
        _rssModel.setUserId(rss.getUserId());
        _rssModel.setUrl(rss.getUrl());
        _rssModel.setChannelTitle(rss.getChannel().getTitle());
        for (String a : rss.getChannel().titleItemList()){
            realmStringList.add(a);
        }
        _rssModel.setItemListTitle(realmStringList);
        _realm.commitTransaction();
    }

    private void createRssModel(Rss rss) {

    }

    final private static String TAG = NewRssFragment.class.getName();

    private Authorization _authorization;
    private Realm _realm;
    private RealmResults<RssModel> _rssResults;
    private RssModel _rssModel;
    private RssListViewModel _rssListViewModel;
    private String _token;
    private String _userId;
    private View _view;
    private EditText _urlEditText;
}
