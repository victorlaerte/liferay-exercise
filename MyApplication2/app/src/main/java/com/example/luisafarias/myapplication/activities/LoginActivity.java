package com.example.luisafarias.myapplication.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.example.luisafarias.myapplication.R;
import com.example.luisafarias.myapplication.model.WeDeployActions;
import com.example.luisafarias.myapplication.util.Constants;
import com.wedeploy.android.Callback;
import com.wedeploy.android.WeDeploy;
import com.wedeploy.android.auth.TokenAuthorization;
import com.wedeploy.android.exception.WeDeployException;
import com.wedeploy.android.transport.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_sharedPref = getSharedPreferences(Constants.USER, this.MODE_PRIVATE);
		if (_sharedPref.contains(Constants.TOKEN) && _sharedPref.contains(
			Constants.USER_ID)) {
			String token = _sharedPref.getString(Constants.TOKEN, "");
			String userID = _sharedPref.getString(Constants.USER_ID, "");

			//			Intent intent = new Intent(this, MainActivity.class);
			//			Bundle extra = new Bundle();
			//			extra.putString(Constants.TOKEN_KEY, token);
			//			extra.putString(Constants.USER_ID, userID);
			//			intent.putExtra(Constants.TOKEN_USER_ID, extra);
			//			finish();
			//			startActivity(intent);

			openMainActivity(token, userID);
		}
		setContentView(R.layout.activity_login);
	}

	public void loginButton(View view) throws JSONException, WeDeployException {
		EditText editTextLogin = (EditText) findViewById(R.id.emailogin);
		String emailLogin = editTextLogin.getText().toString();
		EditText editTextSenha = (EditText) findViewById(R.id.senhalogin);
		String passwordLogin = editTextSenha.getText().toString();
		login(emailLogin, passwordLogin, view);
	}

	public void login(String emailLogin, String passwordLogin, final View view)
		throws WeDeployException, JSONException {

		WeDeployActions.getInstance().login(emailLogin, passwordLogin, new WeDeployActions.CallbackLogin() {
			@Override
			public void onSuccess(String token, String userID) {
				saveUser(userID);
				openMainActivity(token,userID);
			}

			@Override
			public void onFailure(Exception e) {
				Log.e("LoginActivity",e.getMessage());
				Snackbar.make(view,e.getMessage(), Snackbar.LENGTH_LONG).show();
			}
		});/***alteracoes aqui dentro**/



		//		final Intent intent = new Intent(this, MainActivity.class);
		//		final Bundle extra = new Bundle();


//		_weDeploy.auth(Constants.AUTH_URL)
//			.signIn(emailLogin, passwordLogin)
//			.execute(new Callback() {
//				public void onSuccess(Response response) {
//					currentUserSuccess(response, view);
//				}
//
//                    public void onFailure(Exception e) {
//                        Log.e(LoginActivity.class.getName(), e.getMessage());
//
//						Snackbar.make(view, e.getMessage(),
//								Snackbar.LENGTH_LONG).show();
//				}
//			});

	}
/***aho que nao usarei mais esse metodo***/
	private void currentUserSuccess(Response response, final View view) {
		Log.d(TAG, "entrei");

		final JSONObject jsonBody;
		try {
			jsonBody = new JSONObject(response.getBody());
			_token = jsonBody.getString(Constants.ACCESS_TOKEN);

			//Log.d("shared token",_sharedPref.getString("token",""));

			WeDeployActions.getInstance()
				.getCurrentUser(new TokenAuthorization(_token),
					new WeDeployActions.CallbackUserID() {
						@Override
						public void onSuccess(String userID) {
							saveUser(userID);
							openMainActivity(_token, userID);
						}

						@Override
						public void onFailure(Exception e) {
							Log.e(LoginActivity.class.getName(),
								e.getMessage());
							Snackbar.make(view, e.getMessage(),
									Snackbar.LENGTH_LONG).show();
						}
					});

		} catch (JSONException e) {
			Log.e(TAG, e.getMessage());
			Snackbar.make(view, e.getMessage(),
					Snackbar.LENGTH_LONG).show();
		}
	}

	private void saveUser(String userID) {
		_sharedPref = getSharedPreferences(Constants.USER, MODE_PRIVATE);
		SharedPreferences.Editor editor = _sharedPref.edit();
		editor.putString(Constants.TOKEN, _token);
		editor.putString(Constants.USER_ID, userID);
		editor.apply();
	}

	public void openMainActivity(String token, String userId) {
		Intent intent = new Intent(this, MainActivity.class);
		Bundle extra = new Bundle();
		extra.putString(Constants.TOKEN_KEY, token);
		extra.putString(Constants.USER_ID, userId);
		intent.putExtra(Constants.TOKEN_USER_ID, extra);
		finish();
		startActivity(intent);
	}

	public void newAccount(View view) {
		Intent intent = new Intent(this, NewUserActivity.class);
		startActivity(intent);
	}

	//private static boolean _login = false;
	private SharedPreferences _sharedPref;
	private static final String TAG = LoginActivity.class.getName();
	private String _token;
	//private String _userID;
	private WeDeploy _weDeploy = new WeDeploy.Builder().build();
    }
