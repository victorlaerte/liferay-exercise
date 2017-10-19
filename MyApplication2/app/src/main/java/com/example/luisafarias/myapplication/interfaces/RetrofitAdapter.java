package com.example.luisafarias.myapplication.interfaces;

import com.example.luisafarias.myapplication.model.Feed;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by luisafarias on 17/10/17.
 */

public interface RetrofitAdapter {
    @GET("/TechCrunch/social")
    Call<Feed> getItems();
}
