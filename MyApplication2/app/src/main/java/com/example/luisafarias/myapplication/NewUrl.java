package com.example.luisafarias.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wedeploy.android.Callback;
import com.wedeploy.android.WeDeploy;
import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.auth.TokenAuthorization;
import com.wedeploy.android.exception.WeDeployException;
import com.wedeploy.android.transport.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class NewUrl extends AppCompatActivity {
    String token1, token;

    WeDeploy weDeploy = new WeDeploy.Builder().build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_url);
        token1 = getIntent().getExtras().getString(token);
    }

    public void addNewUrl(View view) throws JSONException, WeDeployException {
        TextView nomeUrl1 = (TextView) findViewById(R.id.nomeUrl);
        String nomeUrl = nomeUrl1.getText().toString();
        TextView url1 = (TextView) findViewById(R.id.url);
        String url = url1.getText().toString();
        Authorization authorization = new TokenAuthorization(token1);

        JSONObject feedJsonObject = new JSONObject()
                .put("name", nomeUrl)
                .put("userId", token1)
                .put("url", url);

        weDeploy
                .data("https://data-weread.wedeploy.io").authorization(authorization)
                .create("Feeds", feedJsonObject)
                .execute(new Callback() {
                    public void onSuccess(Response response) {
                        Log.d(NewUrl.class.getName(),"salvo com sucesso");
                    }

                    public void onFailure(Exception e) {
                        Log.e(NewUrl.class.getName(),e.getMessage());
                    }
                });

    }
}
