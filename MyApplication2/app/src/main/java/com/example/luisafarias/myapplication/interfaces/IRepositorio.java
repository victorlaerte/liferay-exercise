package com.example.luisafarias.myapplication.interfaces;

import com.example.luisafarias.myapplication.model.Rss;
import com.example.luisafarias.myapplication.model.RssRepositorio;
import com.wedeploy.android.auth.Authorization;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;

/**
 * Created by luisafarias on 26/09/17.
 */

public interface IRepositorio<T> {

	public abstract void addRss(Rss rss, Authorization authorization,
								 RssRepositorio.CallbackRss callbackRss) throws JSONException;

	public abstract void updateRss(Rss rss, Authorization authorization,
									RssRepositorio.CallbackRss callbackRss) throws JSONException;

	public abstract void removeRss(Rss rss, Authorization authorization,
									RssRepositorio.CallbackRss callbackRss);

	public abstract T getRss(Rss rss, Authorization authorization);

	public abstract ArrayList<T> getAllRss(Authorization authorization);

	public abstract List<T> rssList();
}
