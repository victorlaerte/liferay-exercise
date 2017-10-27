package com.example.luisafarias.myapplication.interfaces;

import com.example.luisafarias.myapplication.model.Rss;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by luisafarias on 17/10/17.
 */

public interface WeRetrofitService {

	@GET
	Call<Rss> getItems(@Url String url);
}
