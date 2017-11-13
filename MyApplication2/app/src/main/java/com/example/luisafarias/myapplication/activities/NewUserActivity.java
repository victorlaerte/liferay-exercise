package com.example.luisafarias.myapplication.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.example.luisafarias.myapplication.R;
import com.example.luisafarias.myapplication.model.RssRepositorio;
import com.example.luisafarias.myapplication.model.WeDeployActions;
import com.example.luisafarias.myapplication.util.Constants;
import com.wedeploy.android.Callback;
import com.wedeploy.android.WeDeploy;
import com.wedeploy.android.exception.WeDeployException;
import com.wedeploy.android.transport.Response;

public class NewUserActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_user);
	}

	public void createUser(final View view) throws WeDeployException {
		EditText editTextName = (EditText) findViewById(R.id.box_name);
		final String name = editTextName.getText().toString();
		EditText editTextEmail = (EditText) findViewById(R.id.box_email);
		final String email = editTextEmail.getText().toString();
		EditText editTextPassword = (EditText) findViewById(R.id.box_password);
		String password = editTextPassword.getText().toString();

		WeDeployActions.getInstance().createNewUser(email, password,
				name, new WeDeployActions.CallbackNewUser() {

			@Override
			public void onSuccess(String token, String userId) {
				saveUser(token,userId);
				openMainActivity(token,userId);
			}

			@Override
			public void onFailure(Exception e) {
				Log.e("NewUserActivity",e.getMessage());
				Snackbar.make(view.getRootView().findViewById(R.id.layout_new_user),
						"Erro ao criar usuario",Snackbar.LENGTH_LONG).show();
			}
		});


//		//if (_isValidEmail( email)){
//			_weDeploy.auth(Constants.AUTH_URL)
//					.createUser(email,password,name)
//					.execute(new Callback() {
//						public void onSuccess(Response response) {
//							//_openFeedListActivity();
//							Log.d("NewUserActivity", "Salvou");
//						}
//
//						public void onFailure(Exception e) {
//
//							Log.e(NewUserActivity.class.getName(), e.getMessage());
//							Log.d("NewUserActivity", email);
//						}
//					});
////		}else {
////			Snackbar.make(view, "E-mail invalido!",
////
////					Snackbar.LENGTH_LONG).show();
////			Log.d("NewUserActivity", email);
////		}

	}

	public void saveUser(String userId, String token){
		SharedPreferences sharedP = getSharedPreferences(Constants.USER, MODE_APPEND);
		SharedPreferences.Editor editor = sharedP.edit();
		editor.putString(Constants.TOKEN, token);
		editor.putString(Constants.USER_ID, userId);
		editor.apply();
	}

	public void openMainActivity(String token, String userId){
		Intent intent = new Intent(this,MainActivity.class);
		Bundle extra = new Bundle();
		extra.putString(Constants.TOKEN_KEY, token);
		extra.putString(Constants.USER_ID, userId);
		intent.putExtra(Constants.TOKEN_USER_ID, extra);
		finish();
		startActivity(intent);
	}

//	private void _openFeedListActivity() {
//		Intent intent = new Intent(NewUserActivity.this, MainActivity.class);
//		startActivity(intent);
//	}

	private boolean _isValidEmail(String email){
		if (email== null){
			return false;
		}else {
			return Patterns.EMAIL_ADDRESS.matcher(email).matches();
		}

	}

	private WeDeploy _weDeploy = new WeDeploy.Builder().build();
}
