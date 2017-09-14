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

public class Login extends AppCompatActivity {

    WeDeploy weDeploy = new WeDeploy.Builder().build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view){
        final Intent intent = new Intent(this, SemFeed.class);
        EditText emaillogin = (EditText) findViewById(R.id.emailogin);
        String emailogin = emaillogin.getText().toString();
        EditText senhaalogin = (EditText) findViewById(R.id.senhalogin);
        String senhalogin = senhaalogin.getText().toString();
        weDeploy
                .auth("https://auth-weread.wedeploy.io")
                .signIn(emailogin,senhalogin)
                .execute(new Callback() {
                    public void onSuccess(Response response) {
                        startActivity(intent);
                    }

                    public void onFailure(Exception e) {
                        Log.e(NewUser.class.getName(),e.getMessage());
                    }
                });
    }

    public void novaConta(View view) throws WeDeployException {
        Intent intent = new Intent(this, NewUser.class);
        startActivity(intent);


    }
}
