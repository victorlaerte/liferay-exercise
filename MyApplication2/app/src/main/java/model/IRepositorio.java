package model;

import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.transport.Response;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luisafarias on 26/09/17.
 */

public interface IRepositorio<T> {

    public abstract void addFeed(Feed feed, Authorization authorization, Repositorio.CallbackFeed callbackFeed) throws JSONException;
    public abstract void updateFeed(Feed feed, Authorization authorization) throws JSONException;
    public abstract void removeFeed(Feed feed, Authorization authorization);
    public abstract T getFeed(Feed feed, Authorization authorization);
    public abstract ArrayList<T> getAllFeeds(Authorization authorization);
    public abstract List<T> feedList();
}
