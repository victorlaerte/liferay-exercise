package com.example.luisafarias.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.example.luisafarias.myapplication.R;
import com.example.luisafarias.myapplication.model.RssRepositorio;
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

	public void createUser(View view) throws WeDeployException {
		EditText editTextName = (EditText) findViewById(R.id.box_name);
		String name = editTextName.getText().toString();
		EditText editTextEmail = (EditText) findViewById(R.id.box_email);
		final String email = editTextEmail.getText().toString();
		EditText editTextPassword = (EditText) findViewById(R.id.box_password);
		String password = editTextPassword.getText().toString();

		//if (_isValidEmail( email)){
			_weDeploy.auth(Constants.AUTH_URL)
					.createUser(email,password,name)
					.execute(new Callback() {
						public void onSuccess(Response response) {
							_openFeedListActivity();
						}

						public void onFailure(Exception e) {

							Log.e(NewUserActivity.class.getName(), e.getMessage());
							Log.d("NewUserActivity", email);
						}
					});
//		}else {
//			Snackbar.make(view, "E-mail invalido!",
//
//					Snackbar.LENGTH_LONG).show();
//			Log.d("NewUserActivity", email);
//		}

	}

	private void _openFeedListActivity() {
		Intent intent = new Intent(NewUserActivity.this, MainActivity.class);
		startActivity(intent);
	}

	private boolean _isValidEmail(String email){
		if (email== null){
			return false;
		}else {
			return Patterns.EMAIL_ADDRESS.matcher(email).matches();
		}

	}

	private WeDeploy _weDeploy = new WeDeploy.Builder().build();
}
