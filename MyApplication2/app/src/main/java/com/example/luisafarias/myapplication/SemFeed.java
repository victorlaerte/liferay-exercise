package com.example.luisafarias.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wedeploy.android.Callback;
import com.wedeploy.android.WeDeploy;
import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.auth.TokenAuthorization;
import com.wedeploy.android.exception.WeDeployException;
import com.wedeploy.android.transport.Response;

import org.json.JSONObject;

public class SemFeed extends AppCompatActivity {
    WeDeploy weDeploy = new WeDeploy.Builder().build();
    String token, token1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem_feed);
        Toolbar myToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        token1 = getIntent().getExtras().getString(token);


    }

    public void testandologin(View view){

        weDeploy
                .auth("https://auth-weread.wedeploy.io")
                .signIn("luisa@gmail.com","1234")
                .execute(new Callback() {
                    public void onSuccess(Response response) {

                        Log.d(Login.class.getName(),"entrei");
                        //startActivity(intent);
                    }

                    public void onFailure(Exception e) {
                        Log.e(NewUser.class.getName(),e.getMessage());
                    }
                });
    }

    public void goAddUrl(View view){
        Intent intent = new Intent(this,NewUrl.class);
        intent.putExtra(token,token1);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_feed,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        String token = "";
        int id = item.getItemId();
        //String token1 = getIntent().getExtras().getString(token);
        Log.d("esse é o token",token1);


        if(id == R.id.logout) {


            Authorization authorization = new TokenAuthorization(token1);

            weDeploy
                    .auth("https://auth-weread.wedeploy.io").authorization(authorization)
                    .signOut()
                    .execute(new Callback() {
                        public void onSuccess(Response response) {
                            Log.d(SemFeed.class.getName(), "saiu");

                        }

                        public void onFailure(Exception e) {
                            Log.e(SemFeed.class.getName(), e.getMessage());
                        }
                    });
        }

        return super.onOptionsItemSelected(item);

    }
}
