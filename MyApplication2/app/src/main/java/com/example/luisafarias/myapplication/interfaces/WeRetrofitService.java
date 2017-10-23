package com.example.luisafarias.myapplication.interfaces;

import com.example.luisafarias.myapplication.model.AnswersResponse;
import com.example.luisafarias.myapplication.model.Feed;
import com.example.luisafarias.myapplication.model.FeedItem;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;

/**
 * Created by luisafarias on 17/10/17.
 */

public interface WeRetrofitService {

    @GET("dynamo/brasil/rss2.xml")
    Call<Feed> getItems();
}
