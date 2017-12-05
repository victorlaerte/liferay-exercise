package com.example.luisafarias.myapplication.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.example.luisafarias.myapplication.R;
import com.example.luisafarias.myapplication.model.User;
import com.example.luisafarias.myapplication.model.WeDeployActions;
import com.example.luisafarias.myapplication.util.Constants;
import com.wedeploy.android.Callback;
import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.auth.TokenAuthorization;
import com.wedeploy.android.exception.WeDeployException;
import com.wedeploy.android.transport.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		_sharedPref = getSharedPreferences(Constants.USER, MODE_PRIVATE);

		ifLoggedInMainActivity();
		setContentView(R.layout.activity_login);

		User userViewModel = ViewModelProviders.of(this).get(User.class);
		_editTextLogin = findViewById(R.id.emailogin);
		_editTextPassword = findViewById(R.id.senhalogin);

		if (userViewModel.getEmail() != null
			|| userViewModel.getPassWord() != null) {

			_editTextLogin.setText(userViewModel.getEmail());
			_editTextPassword.setText(userViewModel.getPassWord());
		} else {
			userViewModel.setEmail(_editTextLogin.getText().toString());
			userViewModel.setPassWord(_editTextPassword.getText().toString());
		}
	}

	private void ifLoggedInMainActivity() {
		if (_sharedPref.contains(Constants.TOKEN) && _sharedPref.contains(
			Constants.USER_ID)) {
			String token = _sharedPref.getString(Constants.TOKEN, "");
			String userID = _sharedPref.getString(Constants.USER_ID, "");

			Log.d(CLASS_NAME, token + " " + userID);

			openMainActivity(token, userID);
		}
	}

	protected void loginButton(View view)
		throws JSONException, WeDeployException {
		String emailLogin = _editTextLogin.getText().toString();
		String passwordLogin = _editTextPassword.getText().toString();
		login(emailLogin, passwordLogin);
	}

	private void login(String emailLogin, String passwordLogin)
		throws WeDeployException, JSONException {

		WeDeployActions.getInstance()
			.login(emailLogin, passwordLogin, getLoginCallback());
	}

	private void getCurrentUser(String token, String userID) {
		_sharedPref = getSharedPreferences(Constants.USER, MODE_PRIVATE);
		SharedPreferences.Editor editor = _sharedPref.edit();
		editor.putString(Constants.TOKEN, token);
		editor.putString(Constants.USER_ID, userID);
		editor.apply();
	}

	private void openMainActivity(String token, String userId) {
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

	private Callback getLoginCallback() {

		return new Callback() {
			@Override
			public void onSuccess(Response response) {
				try {
					JSONObject json = new JSONObject(response.getBody());
					final String token = json.getString(Constants.ACCESS_TOKEN);
					Authorization authorization = new TokenAuthorization(token);

					getCurrentUser(token, authorization);
				} catch (JSONException e) {
					Snackbar.make(findViewById(R.id.loginActivity),
						R.string.nao_foi_possivel_login, Snackbar.LENGTH_LONG)
						.show();
					Log.e(CLASS_NAME, e.getMessage());
				}
			}

			@Override
			public void onFailure(Exception e) {
				Snackbar.make(findViewById(R.id.loginActivity),
					R.string.nao_foi_possivel_login, Snackbar.LENGTH_LONG)
					.show();
				Log.e(CLASS_NAME, e.getMessage());
			}
		};
	}

	private void getCurrentUser(final String token,
		Authorization authorization) {
		WeDeployActions.getInstance()
			.getCurrentUser(authorization, new Callback() {
				@Override
				public void onSuccess(Response response) {

					try {
						JSONObject json = new JSONObject(response.getBody());
						String userId = json.getString(Constants.ID);
						getCurrentUser(token, userId);
						openMainActivity(token, userId);
					} catch (JSONException e) {
						Snackbar.make(findViewById(R.id.loginActivity),
							R.string.nao_foi_possivel_acessar_usuario,
							Snackbar.LENGTH_LONG).show();
						Log.e(CLASS_NAME, e.getMessage());
					}
				}

				@Override
				public void onFailure(Exception e) {
					Snackbar.make(findViewById(R.id.loginActivity),
						R.string.nao_foi_possivel_acessar_usuario,
						Snackbar.LENGTH_LONG).show();
					Log.e(CLASS_NAME, e.getMessage());
				}
			});
	}

	private EditText _editTextLogin;
	private EditText _editTextPassword;
	private SharedPreferences _sharedPref;
	final private String CLASS_NAME = "RssListFragment";
}
