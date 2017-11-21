package com.example.luisafarias.myapplication.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import com.example.luisafarias.myapplication.model.Rss;
import com.example.luisafarias.myapplication.model.RssRepository;
import com.example.luisafarias.myapplication.util.Constants;
import com.wedeploy.android.Callback;
import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.auth.TokenAuthorization;
import com.wedeploy.android.transport.Response;

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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Deseja excluir " + nome)
                .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (_rss != null && _authorization != null) {
                            RssRepository.getInstance().removeRss(_rss, _authorization, new Callback() {
                                @Override
                                public void onSuccess(Response response) {
                                    Log.d("PopUpFragment", "excluido com sucesso");
                                }

                                @Override
                                public void onFailure(Exception e) {
                                    Log.e("PopUpFragment", e.getMessage());
                                }
                            });

                        }

                    }
                })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
        return builder.create();
    }

    @Override
    public void onPause() {
        FragmentManager fm = (getActivity()).getFragmentManager();
        RssListFragment fld = (RssListFragment) fm.findFragmentByTag(Constants.GET_RSS_LIST_FRAGMENT);
        fld.reloadFeeds();
        Log.d("PopUpFragment", "OnPause");
        super.onPause();
    }

    private Authorization _authorization;
    private Rss _rss;
}
