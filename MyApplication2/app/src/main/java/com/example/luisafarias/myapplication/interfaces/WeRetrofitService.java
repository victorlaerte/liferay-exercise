package com.example.luisafarias.myapplication.interfaces;

import com.example.luisafarias.myapplication.model.Feed;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by luisafarias on 17/10/17.
 */

public interface WeRetrofitService {

	//TODO https://stackoverflow.com/questions/32559333/retrofit-2-dynamic-url
	@GET
	Call<Feed> getItems(@Url String url);
}
