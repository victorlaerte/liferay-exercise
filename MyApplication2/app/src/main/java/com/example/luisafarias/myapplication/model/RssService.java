package com.example.luisafarias.myapplication.model;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.luisafarias.myapplication.interfaces.RetrofitAdapter;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by luisafarias on 20/10/17.
 */

public class RssService extends Service {

    private static final String RSS_LINK = "http://feeds.feedburner.com/";
    public static final String ITEMS = "items";
    public static final String RECEIVER = "receiver";
    private final static String TAG = "RssService";
    private final static int UPDATE_INTERVAL_MIN = 10;
    List<FeedItem> cachedList = null;
    Intent mItent;

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        Log.d(TAG,"Service started");
        mItent = intent;
        updateRssItems();
        //sendCachedList();
        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarm.set(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + (1000 * 60 * UPDATE_INTERVAL_MIN),
                PendingIntent.getService(getApplicationContext(), 0,
                        new Intent(this, RssService.class), 0));
        return Service.START_STICKY;
    }

    void updateRssItems(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        SimpleXmlConverterFactory conv  = SimpleXmlConverterFactory.createNonStrict();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RSS_LINK)
                .client(client)
                .addConverterFactory(conv)
                .build();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
