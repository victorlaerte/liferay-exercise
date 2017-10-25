package com.example.luisafarias.myapplication.model;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by luisafarias on 20/10/17.
 */

public class RetrofitClient {

	private static Retrofit instance = null;

	public static Retrofit getInstance(String baseUrl) {
		if (instance == null) {
			instance = new Retrofit.Builder().baseUrl(baseUrl)
				.addConverterFactory(SimpleXmlConverterFactory.create())
				.build();
		}

		return instance;
	}
}
