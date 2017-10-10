package com.example.luisafarias.myapplication.model;

import android.util.Log;

import com.example.luisafarias.myapplication.util.Constants;
import com.wedeploy.android.Callback;
import com.wedeploy.android.WeDeploy;
import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.transport.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by luisafarias on 10/10/17.
 */

public class WeDeployActions {

    private static WeDeployActions _uniqueInstance;

    private WeDeployActions(){

    }

    public static WeDeployActions getInstance() {
        if(_uniqueInstance == null){
            _uniqueInstance = new WeDeployActions();
        }
        return _uniqueInstance;
    }

    public void getCurrentUser(Authorization authorization, final CallbackUserID callbackUserID){

        _weDeploy.auth(Constants.AUTH_URL)
                .authorization(authorization)
                .getCurrentUser()
                .execute(new Callback() {
                    @Override
                    public void onSuccess(Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.getBody());
                            _userID = jsonObject.getString("id");

                            callbackUserID.onSucces(_userID);

                        } catch (JSONException e) {
                            callbackUserID.onFailure(e);
                            e.printStackTrace();
                            Log.e(WeDeployActions.class.getName(),e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(Exception e) {
                        callbackUserID.onFailure(e);
                        Log.e(WeDeployActions.class.getName(),e.getMessage());
                    }
                });
    }

    public interface CallbackUserID{
        void onSucces(String userID);
        void onFailure(Exception e);
    }

    private String _userID;
    private WeDeploy _weDeploy = new WeDeploy.Builder().build();


}
