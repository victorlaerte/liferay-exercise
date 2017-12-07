package com.example.luisafarias.myapplication.dao;

import com.example.luisafarias.myapplication.model.Item;
import com.example.luisafarias.myapplication.model.Rss;
import com.example.luisafarias.myapplication.model.RssModel;
import com.example.luisafarias.myapplication.util.Constants;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by luisafarias on 05/12/17.
 */

public class RssDAO {
    private static RssDAO _uniqueInstance;

    private RssDAO(){
        _realm = Realm.getDefaultInstance();
    }

    public static RssDAO getInstance(){
         if (_uniqueInstance == null){
             _uniqueInstance = new RssDAO();
         }

         return _uniqueInstance;
    }

    public void addRssRealm(Rss rss, Rss rssId) {

        _realm.executeTransaction(realm -> {

            RealmResults<RssModel> rssResults = _realm.where(RssModel.class).equalTo(
                    Constants._ID,rssId.getId()).findAll();
            if (rssResults.isEmpty()){
                RealmList<String> realmStringList = new RealmList<>();
                RssModel rssModel = _realm.createObject(RssModel.class, rssId.getId());
                rssModel.setChannelTitle(rss.getChannel().getTitle());
                for (Item item : rss.getChannel().getItem()) {
                    realmStringList.add(item.getTitle());
                }
                rssModel.setItemListTitle(realmStringList);
            }

        });

    }

    public void deleteRealmRss(Rss rss) {
        _realm.executeTransaction(realm -> {
            RealmResults<RssModel> rssResults = realm.where(RssModel.class).
                    equalTo(Constants._ID, rss.getId()).findAll();

            rssResults.deleteAllFromRealm();
        });
    }

    private Realm _realm;
}
