package com.example.luisafarias.myapplication.model;

import android.util.Log;
import com.example.luisafarias.myapplication.util.Constants;
import com.wedeploy.android.Callback;
import com.wedeploy.android.WeDeploy;
import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.auth.TokenAuthorization;
import com.wedeploy.android.transport.Response;
import org.json.JSONException;
import org.json.JSONObject;

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

	public void login(final String email, String password, final CallbackLogin callbackLogin){
		_weDeploy.auth(Constants.AUTH_URL)
				.signIn(email,password)
				.execute(new Callback() {
					@Override
					public void onSuccess(Response response) {
						Log.d("WeDeployActions", email);
						try {
							JSONObject jsonObject = new JSONObject(response.getBody());
							final String token = jsonObject.getString(Constants.ACCESS_TOKEN);
							Authorization au = new TokenAuthorization(token);
							getCurrentUser(au, new CallbackUserID() {
								@Override
								public void onSuccess(String userID) {

									callbackLogin.onSuccess(token,userID);
								}

								@Override
								public void onFailure(Exception e) {
									callbackLogin.onFailure(e);

								}
							});
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(Exception e) {
						Log.e("WeDeployActions", e.getMessage());
					}
				});
	}

	public void getCurrentUser(Authorization authorization,
		final CallbackUserID callbackUserID) {

		_weDeploy.auth(Constants.AUTH_URL)
			.authorization(authorization)
			.getCurrentUser()
			.execute(new Callback() {
				@Override
				public void onSuccess(Response response) {
					try {
						JSONObject jsonObject =
							new JSONObject(response.getBody());
						_userID = jsonObject.getString("id");

						callbackUserID.onSuccess(_userID);
					} catch (JSONException e) {
						callbackUserID.onFailure(e);
						e.printStackTrace();
						Log.e(WeDeployActions.class.getName(), e.getMessage());
					}
				}

				@Override
				public void onFailure(Exception e) {
					callbackUserID.onFailure(e);
					Log.e(WeDeployActions.class.getName(), e.getMessage());
				}
			});
	}

	public void createNewUser(final String email, final String password, String name,
							  final CallbackNewUser callbackNewUser){
		_weDeploy.auth(Constants.AUTH_URL)
				.createUser(email,password,name)
				.execute(new Callback() {
					@Override
					public void onSuccess(Response response) {
						login(email, password, new CallbackLogin() {
							@Override
							public void onSuccess(String token, String userID) {

								callbackNewUser.onSuccess(token,userID);
							}

							@Override
							public void onFailure(Exception e) {
								Log.e("WeDeployActions", e.getMessage());
							}
						});

					}

					@Override
					public void onFailure(Exception e) {
						Log.e("WeDeployActions", e.getMessage());
					}
				});
	}

	public interface CallbackUserID {
		void onSuccess(String userID);

		void onFailure(Exception e);
	}

	public interface CallbackLogin {
		void onSuccess(String token, String userID);

		void onFailure(Exception e);
	}

	public interface CallbackNewUser {
		void onSuccess(String token, String userId);
		void onFailure(Exception e);
	}

	private String _userID;
	private WeDeploy _weDeploy = new WeDeploy.Builder().build();
}
