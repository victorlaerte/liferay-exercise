package com.example.luisafarias.myapplication.model;

import android.content.Context;
import android.util.Log;

import com.example.luisafarias.myapplication.FeedListActivity;
import com.example.luisafarias.myapplication.NewUrlActivity;
import com.wedeploy.android.Callback;
import com.wedeploy.android.WeDeploy;
import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.transport.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luisafarias on 26/09/17.
 */

public class Repositorio implements IRepositorio {

    private static Repositorio uniqueInstance;
    WeDeploy weDeploy = new WeDeploy.Builder().build();
    String nomeUrl,userId,url;
    Feed feed;
    ArrayList<Feed> feedList = new ArrayList();
    //ArrayList<String> nomeLista = new ArrayList();

    private Repositorio() {
    }

    public static Repositorio getInstance(Context context) {
        if (uniqueInstance == null) {
            uniqueInstance = new Repositorio();
        }
        return uniqueInstance;
    }


    @Override
    public void addFeed(Feed feed, Authorization authorization, final CallbackFeed callbackFeed) throws JSONException {
        nomeUrl = feed.getNome();
        userId = feed.getUserId();
        url = feed.getUrl();

        if(feed!=null){

            if(!feedList.contains(feed)){
                feedList.add(feed);
            }
            JSONObject feedJsonObject = new JSONObject()
                .put("name", nomeUrl)
                .put("userId", userId)
                .put("url", url);

            weDeploy
                .data("https://data-weread.wedeploy.io").authorization(authorization)
                .create("Feeds", feedJsonObject)
                .execute(new Callback() {
                    public void onSuccess(Response response) {
                        Log.d(NewUrlActivity.class.getName(), "salvo com sucesso");

                        try {
                            JSONObject jsonBody = new JSONObject(response.getBody());

                            Feed feed = new Feed();
                            feed.setNome(jsonBody.getString("name"));
                            feed.setUrl(jsonBody.getString("url"));
                            feed.setId(jsonBody.getString("id"));

                            callbackFeed.onSuccess(feed);
                        } catch (Exception e) {
                            callbackFeed.onFailure(e);
                        }
                    }

                    public void onFailure(Exception e) {
                        Log.e(NewUrlActivity.class.getName(),e.getMessage());
                        callbackFeed.onFailure(e);
                    }
                });
        }
    }

    @Override
    public void updateFeed(Feed feed, Authorization authorization) throws JSONException {
        nomeUrl = feed.getNome();
        url = feed.getUrl();

        if(feed!= null) {

            JSONObject feedJsonObject = new JSONObject()
                    .put("name", nomeUrl)
                    .put("url", url);

            weDeploy
                    .data("https://data-weread.wedeploy.io").authorization(authorization)
                    .update("Feeds/" + feed.getId(), feedJsonObject) //nao foi testado ainda, pego o id quando listo os feeds
                    .execute(new Callback() {
                        public void onSuccess(Response response) {
                            Log.d(NewUrlActivity.class.getName(), "editado com sucesso");
                        }

                        public void onFailure(Exception e) {
                            Log.e(NewUrlActivity.class.getName(), e.getMessage());
                        }
                    });
        }

    }

    @Override
<<<<<<< HEAD:MyApplication2/app/src/main/java/com/example/luisafarias/myapplication/model/Repositorio.java
    public void removeFeed(Feed feed, Authorization authorization) {
        String id = feed.getId();

        weDeploy.data("https://data-weread.wedeploy.io").authorization(authorization)
                .delete("Feeds/"+id).execute(new Callback() {
            @Override
            public void onSuccess(Response response) {
                Log.d(Repositorio.class.getName(),"removeu");
                // TODO call onSuccess
            }

            @Override
            public void onFailure(Exception e) {
                Log.e(Repositorio.class.getName(),e.getMessage());
            }
        });
=======
    public void removeFeed(final Feed feed, Authorization authorization) {

       if(feed!=null /*&& feedList.contains(feed)*/){
           String id = feed.getId();
           Log.d(Repositorio.class.getName(),id);

           weDeploy.data("https://data-weread.wedeploy.io").authorization(authorization)
                   .delete("Feeds/"+id).execute(new Callback() {
               @Override
               public void onSuccess(Response response) {
                   Log.d(Repositorio.class.getName(),"removeu");
                   feedList.remove(feed);
               }

               @Override
               public void onFailure(Exception e) {
                   Log.e(Repositorio.class.getName(),e.getMessage());
               }
           });

       }
>>>>>>> delete updates:MyApplication2/app/src/main/java/model/Repositorio.java

    }

    @Override
    public Feed getFeed(Feed feed, Authorization authorization) {
        return null;
    }

    @Override
    public ArrayList<Feed> getAllFeeds(Authorization authorization) {
        weDeploy
                .data("https://data-weread.wedeploy.io")
                .authorization(authorization)
                .get("Feeds")
                .execute(new Callback() {
                    public void onSuccess(Response response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response.getBody());

                            for(int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonBody = (JSONObject) jsonArray.get(i);
                                String nome = jsonBody.getString("name");
                                String url = jsonBody.getString("url");
                                String userId = jsonBody.getString("userId");
                                String id = jsonBody.getString("id");
                                feed = new Feed(nome,url,userId);
                                feed.setId(id);
                                feedList.add(feed);
                                String jsonBodyString = jsonBody.toString();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    public void onFailure(Exception e) {
                        Log.e(FeedListActivity.class.getName(), e.getMessage());
                    }
                });
        return feedList;
    }

    @Override
    public List<Feed> feedList() {
        return null;
    }

    public void feedListAll(Authorization authorization, final CallbackFeeds callbackFeeds){
        weDeploy
                .data("https://data-weread.wedeploy.io")
                .authorization(authorization)
                .get("Feeds")
                .execute(new Callback() {
                    @Override
                    public void onSuccess(Response response) {
                        //ArrayList<String> listaNomes = new ArrayList<String>();
                        try {
                            JSONArray jsonArray = new JSONArray(response.getBody());

                            List<Feed> listaFeed = new ArrayList<Feed>();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonBody = (JSONObject) jsonArray.get(i);
                                Feed feed = new Feed();
                                feed.setNome(jsonBody.getString("name"));
                                feed.setUrl(jsonBody.getString("url"));
                                feed.setId(jsonBody.getString("id"));
                                listaFeed.add(feed);

                            }

                            callbackFeeds.onSuccess(listaFeed);

                        }
                        catch (Exception e) {
                            callbackFeeds.onFailure(e);
                        }
                    }

                    @Override
                    public void onFailure(Exception e) {
                        callbackFeeds.onFailure(e);
                    }
                });

    }

    public interface CallbackFeeds {
        void onSuccess(List<Feed> feedList);
        void onFailure(Exception e);
    }

    public interface CallbackFeed {
        void onSuccess(Feed feed);
        void onFailure(Exception e);
    }


}
