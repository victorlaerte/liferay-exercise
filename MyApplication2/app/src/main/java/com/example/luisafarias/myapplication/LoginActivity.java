package com.example.luisafarias.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.luisafarias.myapplication.util.Constants;
import com.wedeploy.android.Callback;
import com.wedeploy.android.WeDeploy;
import com.wedeploy.android.exception.WeDeployException;
import com.wedeploy.android.transport.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) throws WeDeployException, JSONException {
        final Intent intent = new Intent(this, FeedListActivity.class);

        EditText editTextLogin = (EditText) findViewById(R.id.emailogin);
        String emaiLogin = editTextLogin.getText().toString();
        EditText editTextSenha = (EditText) findViewById(R.id.senhalogin);
        String senhaLogin = editTextSenha.getText().toString();

        _weDeploy.auth(Constants.AUTH_URL)
                .signIn(emaiLogin,senhaLogin)
                .execute(new Callback() {
                    public void onSuccess(Response response) {
                        Log.d(TAG,"entrei");

                        JSONObject jsonBody;
                        try {
                            jsonBody = new JSONObject(response.getBody());
                            _token = jsonBody.getString("access_token");
                            Log.d("_token", _token);
                            intent.putExtra("tokenKey", _token);

                            finish();
                            startActivity(intent);
                        } catch (JSONException e) {
                            Log.e(TAG, e.getMessage());
                        }

                        /*
                         * TODO: Toast, Snackbar
                         */
                    }

                    public void onFailure(Exception e) {
                        Log.e(NewUserActivity.class.getName(),e.getMessage());

                        /*
                         * TODO: Toast, Snackbar
                         * e.getMessage();
                         */
                    }
                });
    }

    public void novaConta(View view) {
        Intent intent = new Intent(this, NewUserActivity.class);
        startActivity(intent);
    }

    private static final String TAG = LoginActivity.class.getName();
    private String _token;
    private WeDeploy _weDeploy = new WeDeploy.Builder().build();
}
