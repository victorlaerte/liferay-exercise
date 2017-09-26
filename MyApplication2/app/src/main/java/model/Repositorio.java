package model;

import android.util.Log;

import com.example.luisafarias.myapplication.Login_Activity;
import com.example.luisafarias.myapplication.NewUrl_Activity;
import com.wedeploy.android.Callback;
import com.wedeploy.android.WeDeploy;
import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.auth.TokenAuthorization;
import com.wedeploy.android.transport.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luisafarias on 26/09/17.
 */

public class Repositorio implements IRepositorio {

    WeDeploy weDeploy = new WeDeploy.Builder().build();
    String nomeUrl,userId,url;

    @Override
    public void addFeed(Feed feed, Authorization authorization) throws JSONException {
        nomeUrl = feed.getNome();
        userId = feed.getUserId();
        url = feed.getUrl();

        if(feed!=null){

        JSONObject feedJsonObject = new JSONObject()
                .put("name", nomeUrl)
                .put("userId", userId)
                .put("url", url);

        weDeploy
                .data("https://data-weread.wedeploy.io").authorization(authorization)
                .create("Feeds", feedJsonObject)
                .execute(new Callback() {
                    public void onSuccess(Response response) {
                        Log.d(NewUrl_Activity.class.getName(),"salvo com sucesso");
                    }

                    public void onFailure(Exception e) {
                        Log.e(NewUrl_Activity.class.getName(),e.getMessage());
                    }
                });
        }
    }

    @Override
    public void updateFeed(Feed feed, Authorization authorization) {

    }

    @Override
    public void removeFeed(Feed feed, Authorization authorization) {

    }

    @Override
    public Feed getFeed(Feed feed, Authorization authorization) {
        return null;
    }

    @Override
    public ArrayList<Feed> getAllFeeds() {
        return null;
    }

    @Override
    public List<Feed> feedList() {
        return null;
    }


}
