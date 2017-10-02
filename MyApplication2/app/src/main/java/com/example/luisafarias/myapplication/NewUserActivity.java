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

public class NewUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
    }

    public void createUser(View view) throws WeDeployException {
        EditText editTextNome = (EditText) findViewById(R.id.caixanomee);
        String nome = editTextNome.getText().toString();
        EditText editTextEmail = (EditText) findViewById(R.id.caixaemaill);
        String email = editTextEmail.getText().toString();
        EditText editTextSenha = (EditText) findViewById(R.id.caixaSenha);
        String senha = editTextSenha.getText().toString();

        _weDeploy.auth("https://auth-weread.wedeploy.io")
                .createUser(email, senha, nome)
                .execute(new Callback() {
                    public void onSuccess(Response response) {
                        _openFeedListActivity();
                    }

                    public void onFailure(Exception e) {
                        Log.e(NewUserActivity.class.getName(),e.getMessage());
                    }
                });
    }

    private void _openFeedListActivity() {
        Intent intent = new Intent(NewUserActivity.this, FeedListActivity.class);
        startActivity(intent);
    }

    private WeDeploy _weDeploy = new WeDeploy.Builder().build();
}
