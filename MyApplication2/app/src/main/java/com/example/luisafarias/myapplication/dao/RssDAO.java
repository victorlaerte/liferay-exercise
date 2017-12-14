package com.example.luisafarias.myapplication.dao;

import com.example.luisafarias.myapplication.model.Channel;
import com.example.luisafarias.myapplication.model.Item;
import com.example.luisafarias.myapplication.model.Rss;
import com.example.luisafarias.myapplication.model.RssModel;
import com.example.luisafarias.myapplication.util.Constants;

import java.util.ArrayList;
import java.util.List;

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
                rssModel.setFavorite(rss.getFavorite());
                if ( rss.getChannel().getItem() != null){
                    for (Item item : rss.getChannel().getItem()) {
                        realmStringList.add(item.getTitle());
                    }
                    rssModel.setItemListTitle(realmStringList);
                }

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

    public void setRealmRss(Rss rss, Rss rssId){
        deleteRealmRss(rss);
        addRssRealm(rss,rssId);
    }

    public List<Rss> rssModelToRss() {
        List<RssModel>rssResults = _realm.where(RssModel.class).findAll();
        List<Rss> rssList = new ArrayList();
        List<Item> itemList = new ArrayList<>();

        for (RssModel a : rssResults) {
            Rss rss = new Rss();
            Channel channel = new Channel();
            rss.setChannel(channel);
            rss.setId(a.getId());
            rss.getChannel().setTitle(a.getChannelTitle());

            for (String b : a.getItemListTitle()) {
                Item item = new Item();
                item.setTitle(b);
                itemList.add(item);
            }

            rss.getChannel().setItem(itemList);

            rssList.add(rss);
        }
        return rssList;
    }

    private Realm _realm;
}
