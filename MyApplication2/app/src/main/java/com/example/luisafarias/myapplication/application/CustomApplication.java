package com.example.luisafarias.myapplication.application;

import android.app.Application;

import com.example.luisafarias.myapplication.util.Constants;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by luisafarias on 30/11/17.
 */

public class CustomApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(Constants.REALM_DB)
                //.deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);

    }
}
