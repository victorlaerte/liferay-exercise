package com.example.luisafarias.myapplication;

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

public class NewUrl_Activity extends AppCompatActivity {
    String token, userId;
    Authorization authorization;

    WeDeploy weDeploy = new WeDeploy.Builder().build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_url);
        token = getIntent().getExtras().getString("token");
        userId = getIntent().getExtras().getString("userId");


    }

    public void addNewUrl(View view) throws JSONException, WeDeployException {
        TextView nomeUrl1 = (TextView) findViewById(R.id.nomeUrl);
        String nomeUrl = nomeUrl1.getText().toString();
        TextView url1 = (TextView) findViewById(R.id.url);
        String url = url1.getText().toString();
        authorization = new TokenAuthorization(token);


        JSONObject feedJsonObject = new JSONObject()
                .put("name", nomeUrl)
                .put("userId", userId)
                .put("url", url);

        weDeploy
                .data("https://data-weread.wedeploy.io").authorization(authorization)
                .create("Feeds", feedJsonObject)
                .execute(new Callback() {
                    public void onSuccess(Response response) {
                        Log.d(NewUrl_Activity.class.getName(),"salvo com sucesso");
                    }

                    public void onFailure(Exception e) {
                        Log.e(NewUrl_Activity.class.getName(),e.getMessage());
                    }
                });

    }
}
