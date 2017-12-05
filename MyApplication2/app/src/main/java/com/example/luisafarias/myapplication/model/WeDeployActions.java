package com.example.luisafarias.myapplication.model;

import com.example.luisafarias.myapplication.util.Constants;
import com.wedeploy.android.Callback;
import com.wedeploy.android.WeDeploy;
import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.auth.TokenAuthorization;
import com.wedeploy.android.transport.Response;

/**
 * Created by luisafarias on 10/10/17.
 */

public class WeDeployActions {

	private static WeDeployActions _uniqueInstance;

	private WeDeployActions() {

	}

	public static WeDeployActions getInstance() {
		if (_uniqueInstance == null) {
			_uniqueInstance = new WeDeployActions();
		}
		return _uniqueInstance;
	}

	public void login(final String email, String password, Callback callback) {
		_weDeploy.auth(Constants.AUTH_URL)
			.signIn(email, password)
			.execute(callback);
	}

	public void getCurrentUser(Authorization authorization, Callback callback) {

		_weDeploy.auth(Constants.AUTH_URL)
			.authorization(authorization)
			.getCurrentUser()
			.execute(callback);
	}

	public void createNewUser(final String email, final String password,
		String name, final Callback callback) {
		_weDeploy.auth(Constants.AUTH_URL)
			.createUser(email, password, name)
			.execute(callback);
	}

	public void logoutUser(String token,
		final CallbackLogoutUser callbackLogoutUser) {
		Authorization authorization = new TokenAuthorization(token);
		_weDeploy.auth(Constants.AUTH_URL)
			.authorization(authorization)
			.signOut()
			.execute(new Callback() {
				@Override
				public void onSuccess(Response response) {
					callbackLogoutUser.onSuccess();
				}

				@Override
				public void onFailure(Exception e) {
					callbackLogoutUser.onFailure(e);
				}
			});
	}

	public interface CallbackLogoutUser {
		void onSuccess();

		void onFailure(Exception e);
	}
	
	private WeDeploy _weDeploy = new WeDeploy.Builder().build();
}
