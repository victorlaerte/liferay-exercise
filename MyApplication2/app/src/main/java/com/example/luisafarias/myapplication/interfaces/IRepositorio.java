package com.example.luisafarias.myapplication.interfaces;

import com.example.luisafarias.myapplication.model.Rss;
import com.example.luisafarias.myapplication.model.Repositorio;
import com.wedeploy.android.auth.Authorization;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;

/**
 * Created by luisafarias on 26/09/17.
 */

public interface IRepositorio<T> {

	public abstract void addFeed(Rss rss, Authorization authorization,
								 Repositorio.CallbackFeed callbackFeed) throws JSONException;

	public abstract void updateFeed(Rss rss, Authorization authorization,
									Repositorio.CallbackFeed callbackFeed) throws JSONException;

	public abstract void removeFeed(Rss rss, Authorization authorization,
									Repositorio.CallbackFeed callbackFeed);

	public abstract T getFeed(Rss rss, Authorization authorization);

	public abstract ArrayList<T> getAllFeeds(Authorization authorization);

	public abstract List<T> feedList();
}
