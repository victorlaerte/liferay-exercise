package com.example.luisafarias.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.wedeploy.android.Callback;
import com.wedeploy.android.WeDeploy;
import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.auth.TokenAuthorization;
import com.wedeploy.android.transport.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.wedeploy.android.query.filter.Filter.match;

public class FeedListActivity extends AppCompatActivity {
    WeDeploy weDeploy = new WeDeploy.Builder().build();
    String userId, token;
    Authorization authorization;
    ArrayList allFeeds;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_list);
        Toolbar myToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        token = getIntent().getExtras().getString("tokenKey");
        authorization = new TokenAuthorization(token);
        //allFeeds =



    }

    public void goAddUrl(View view){
        final Intent intent = new Intent(this,NewUrlActivity.class);
        weDeploy
                .auth("https://auth-weread.wedeploy.io")
                .authorization(authorization)
                .getCurrentUser()
                .execute(new Callback() {
                    public void onSuccess(Response response) {
                        Log.d(FeedListActivity.class.getName(),"sim bem aqui");


                        JSONObject jsonBody = null;
                        try {
                            jsonBody = new JSONObject(response.getBody());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            userId = jsonBody.getString("id");
                            Log.d(FeedListActivity.class.getName(),userId);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        intent.putExtra("userId",userId);
                        intent.putExtra("token",token);
                        startActivity(intent);

                    }

                    public void onFailure(Exception e) {
                        Log.e(FeedListActivity.class.getName(),e.getMessage());
                    }
                });

    }

//    public void getData(View view){
//
//        weDeploy
//                .data("https://data-weread.wedeploy.io")
//                .authorization(authorization)
//                .get("Feeds")
//                .execute(new Callback() {
//                             public void onSuccess(Response response) {
//                                 //String responseBody = response.getBody();
//                                 try {
//                                     JSONArray jsonArray = new JSONArray(response.getBody());
//
//                                     for(int i = 0; i < jsonArray.length(); i++) {
//                                         JSONObject jsonBody = (JSONObject) jsonArray.get(i);
//                                         String jsonBodyString = jsonBody.toString();
//                                     }
//                                 } catch (JSONException e) {
//                                     e.printStackTrace();
//                                    }
//                             }
//
//                    public void onFailure(Exception e) {
//                        Log.e(FeedListActivity.class.getName(), e.getMessage());
//                    }
//                });
//
//    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_feed,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        String token = "";
        int id = item.getItemId();
        Log.d("esse é o token",token);


        if(id == R.id.logout) {

            weDeploy
                    .auth("https://auth-weread.wedeploy.io").authorization(authorization)
                    .signOut()
                    .execute(new Callback() {
                        public void onSuccess(Response response) {
                            Log.d(FeedListActivity.class.getName(), "saiu");

                        }

                        public void onFailure(Exception e) {
                            Log.e(FeedListActivity.class.getName(), e.getMessage());
                        }
                    });
        }

        return super.onOptionsItemSelected(item);

    }
}
