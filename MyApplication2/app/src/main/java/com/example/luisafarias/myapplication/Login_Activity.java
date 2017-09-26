package com.example.luisafarias.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.wedeploy.android.Callback;
import com.wedeploy.android.WeDeploy;
import com.wedeploy.android.exception.WeDeployException;
import com.wedeploy.android.transport.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class Login_Activity extends AppCompatActivity {

    WeDeploy weDeploy = new WeDeploy.Builder().build();
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) throws WeDeployException, JSONException {
        final Intent intentSemFeed = new Intent(this, FeedList_Activity.class);
        EditText emailLogin = (EditText) findViewById(R.id.emailogin);
        String emailogin = emailLogin.getText().toString();
        EditText senhaLogin1 = (EditText) findViewById(R.id.senhalogin);
        String senhalogin = senhaLogin1.getText().toString();
        weDeploy
                .auth("https://auth-weread.wedeploy.io")
                .signIn(emailogin,senhalogin)
                .execute(new Callback() {
                    public void onSuccess(Response response) {

                        Log.d(Login_Activity.class.getName(),"entrei");

                        JSONObject jsonBody = null;
                        try {
                            jsonBody = new JSONObject(response.getBody());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            token = jsonBody.getString("access_token");
                            Log.d("token",token);
                            intentSemFeed.putExtra("tokenKey",token);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        startActivity(intentSemFeed);

                    }

                    public void onFailure(Exception e) {
                        Log.e(NewUser_Activity.class.getName(),e.getMessage());
                    }
                });
    }


    public void novaConta(View view) {
        Intent intent = new Intent(this, NewUser_Activity.class);
        startActivity(intent);


    }
}
