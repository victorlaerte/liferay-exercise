package com.example.luisafarias.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wedeploy.android.Callback;
import com.wedeploy.android.WeDeploy;
import com.wedeploy.android.exception.WeDeployException;
import com.wedeploy.android.transport.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class NewUrl extends AppCompatActivity {

    WeDeploy weDeploy = new WeDeploy.Builder().build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_url);
    }

    public void addNewUrl(View view) throws JSONException {
        TextView nomeUrl1 = (TextView) findViewById(R.id.nomeUrl);
        String nomeUrl = nomeUrl1.getText().toString();
        TextView url1 = (TextView) findViewById(R.id.url);
        String url = url1.getText().toString();
        String userId = "userId";
        weDeploy //get userId nao esta funcionando, preciso saber qual o tipo da variavel para pegar o id
                .auth("https://auth-weread.wedeploy.io")
                .getCurrentUser()
                .execute(new Callback() {
                    public void onSuccess(Response response) {
                        // here you receive the movies
                    }

                    public void onFailure(Exception e) {
                        Log.e(NewUser.class.getName(),e.getMessage());
                    }
                });



        JSONObject feedJsonObject = new JSONObject()
                .put("name", nomeUrl)
                .put("userId", userId)
                .put("url", url);

        weDeploy
                .data("https://data-weread.wedeploy.io")
               .create("Feeds", feedJsonObject)
                        .execute(new Callback() {
                            public void onSuccess(Response response) {
                                // here you receive the movies
                            }

                            public void onFailure(Exception e) {
                                Log.e(NewUser.class.getName(),e.getMessage());
                            }
                        });

    }
}
