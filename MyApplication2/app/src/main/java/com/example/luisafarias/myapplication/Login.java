package com.example.luisafarias.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.wedeploy.android.Callback;
import com.wedeploy.android.WeDeploy;
import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.auth.TokenAuthorization;
import com.wedeploy.android.exception.WeDeployException;
import com.wedeploy.android.transport.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    WeDeploy weDeploy = new WeDeploy.Builder().build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) throws WeDeployException, JSONException {
        final Intent intentSemFeed = new Intent(this, SemFeed.class);
        EditText emailLogin = (EditText) findViewById(R.id.emailogin);
        String emailogin = emailLogin.getText().toString();
        EditText senhaLogin1 = (EditText) findViewById(R.id.senhalogin);
        String senhalogin = senhaLogin1.getText().toString();
        String token = null;

//        Response response =
//                weDeploy
//                .auth("https://auth-weread.wedeploy.io")
//                .signIn(emailogin,senhalogin)
//                        .execute(new Callback() {
//                            public void onSuccess(Response response) {
//
//                            }
//
//                            public void onFailure(Exception e) {
//                                // oops something went wrong
//                            }
//                        });

//        JSONObject jsonBody = new JSONObject(response.getBody());
//        String token = jsonBody.getString("access_token");
//
//        Log.d("token:",token);



        weDeploy
                .auth("https://auth-weread.wedeploy.io")
                .signIn(emailogin,senhalogin)
                .execute(new Callback() {
                    String token;
                    String tokenKey;
                    //Bundle bundleToken = intentSemFeed.getExtras();

                    public void onSuccess(Response response) {

                        Log.d(Login.class.getName(),"entrei");

                        JSONObject jsonBody = null;
                        try {
                            jsonBody = new JSONObject(response.getBody());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            token = jsonBody.getString("access_token");
                            Log.d("token",token);
                            intentSemFeed.putExtra(tokenKey,token);
                            //bundleToken.putString(tokenKey,token);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        startActivity(intentSemFeed);

                    }

                    public void onFailure(Exception e) {
                        Log.e(NewUser.class.getName(),e.getMessage());
                    }
                });
    }

//    public void sairDaqui(View view) {
//
//        Log.d("eu n quero pegar","eu mesmo");
////        weDeploy //get current user
////                .auth("https://auth.weread.wedeploy.io")
////                .getCurrentUser()
////                .execute(new Callback() {
////                    public void onSuccess(Response response) {
////                        Log.d(Login.class.getName(),"online");
////                    }
////
////                    public void onFailure(Exception e) {
////                        Log.e(Login.class.getName(),e.getMessage());
////                    }
////                });
//
//        weDeploy
//                .auth("https://auth.weread.wedeploy.io")
//                .signOut()
//                .execute(new Callback() {
//                    public void onSuccess(Response response) {
//
//                    }
//
//                    public void onFailure(Exception e) {
//                        Log.e(Login.class.getName(),e.getMessage());
//                    }
//                });
//    }

    public void novaConta(View view) {
        Intent intent = new Intent(this, NewUser.class);
        startActivity(intent);


    }
}
