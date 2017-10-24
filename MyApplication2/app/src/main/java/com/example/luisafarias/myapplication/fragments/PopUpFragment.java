package com.example.luisafarias.myapplication.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import com.example.luisafarias.myapplication.MainActivity;
import com.example.luisafarias.myapplication.model.Feed;
import com.example.luisafarias.myapplication.model.Repositorio;
import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.auth.TokenAuthorization;

/**
 * Created by luisafarias on 10/10/17.
 */

public class PopUpFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this._feed = getArguments().getParcelable("feed");
        String nome = _feed.get_title();
        String token = getArguments().getString("token");
        _authorization = new TokenAuthorization(token);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("O que deseja fazer com"+nome)
                .setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(_feed != null && _authorization != null){
                            Log.d(_feed.getPartXml(),_feed.getPartMain());/****teste******/
                            MainActivity activity = (MainActivity) getActivity();
                            activity.goEditFeed(_feed);
                        }
                    }
                })
                .setNegativeButton("excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(_feed != null && _authorization != null){
                            Repositorio.getInstance().removeFeed(
                                    _feed,_authorization, new Repositorio.CallbackFeed() {
                                @Override
                                public void onSuccess(Feed feed) {
                                    Snackbar.make(
                                            getView(),"Removido",Snackbar.LENGTH_LONG).show();
                                }

                                @Override
                                public void onFailure(Exception e) {
                                    Snackbar.make(
                                            getView(),e.getMessage(),Snackbar.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });
        return builder.create();
    }

    @Override
    public void onPause() {
        FragmentManager fm =
                (getActivity()).getFragmentManager();
        FeedListFragment fld = (FeedListFragment) fm.findFragmentByTag("test");
        fld.reloadFeeds();
        Log.d("PopUpFragment","OnPause");
        super.onPause();
    }


    private Authorization _authorization;
    private Feed _feed;
}
