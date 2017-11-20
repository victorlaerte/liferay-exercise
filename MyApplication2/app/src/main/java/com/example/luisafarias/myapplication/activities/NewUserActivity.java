package com.example.luisafarias.myapplication.activities;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.example.luisafarias.myapplication.R;
import com.example.luisafarias.myapplication.model.User;
import com.example.luisafarias.myapplication.model.WeDeployActions;
import com.example.luisafarias.myapplication.util.Constants;
import com.wedeploy.android.Callback;
import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.auth.TokenAuthorization;
import com.wedeploy.android.exception.WeDeployException;
import com.wedeploy.android.transport.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class NewUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        User userViewModel = ViewModelProviders.of(this).get(User.class);
        _editTextName = findViewById(R.id.box_name);
        _editTextEmail = findViewById(R.id.box_email);
        _editTextPassword = findViewById(R.id.box_password);

        if (userViewModel.getName() != null || userViewModel.getEmail() != null ||
                userViewModel.getPassWord() != null ){
            _editTextName.setText(userViewModel.getName());
            _editTextEmail.setText(userViewModel.getEmail());
            _editTextPassword.setText(userViewModel.getPassWord());
        }else {
            userViewModel.setName(_editTextName.getText().toString());
            userViewModel.setEmail(_editTextEmail.getText().toString());
            userViewModel.setPassWord(_editTextPassword.getText().toString());
        }

        _constraintLayout = findViewById(R.id.layout_new_user);
    }

    public void createUser(final View view) throws WeDeployException {
        final String name = _editTextName.getText().toString();
        final String email = _editTextEmail.getText().toString();
        final String password = _editTextPassword.getText().toString();

        WeDeployActions.getInstance().createNewUser(email, password,
                name, new Callback() {
                    @Override
                    public void onSuccess(Response response) {
                        Snackbar.make(_constraintLayout, "Usuario criado com sucesso",
                                Snackbar.LENGTH_LONG).show();
                        login(email, password);

                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.e("NewUserActivity", e.getMessage());
                        Snackbar.make(_constraintLayout, "Nao foi possivel criar usuario",
                                Snackbar.LENGTH_LONG).show();
                    }
                });
    }

    private void login(String email, String password) {
        WeDeployActions.getInstance().login(email, password, new Callback() {
            @Override
            public void onSuccess(Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.getBody());
                    final String token = jsonObject.getString(
                            Constants.ACCESS_TOKEN);
                    currentUser(token);

                } catch (JSONException e) {
                    Log.e("NewUserActivity", e.getMessage());
                    Snackbar.make(_constraintLayout, "Nao foi possivel fazer login",
                            Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    public void currentUser(final String token) {
        Authorization authorization = new TokenAuthorization(token);
        WeDeployActions.getInstance().getCurrentUser(authorization,
                new Callback() {
                    @Override
                    public void onSuccess(Response response) {
                        try {
                            JSONObject jsonObject1 = new JSONObject(
                                    response.getBody());
                            String userId = jsonObject1.getString(
                                    Constants.ID);
                            saveUser(token, userId);
                            openMainActivity(token, userId);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.e("NewUserActivity", e.getMessage());
                        Snackbar.make(_constraintLayout, "Nao foi possivel acessar usuario",
                                Snackbar.LENGTH_LONG).show();
                    }
                });
    }


    public void saveUser(String token, String userId) {
        SharedPreferences sharedP = getSharedPreferences(Constants.USER, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedP.edit();
        editor.putString(Constants.TOKEN, token);
        editor.putString(Constants.USER_ID, userId);
        editor.apply();
    }

    public void openMainActivity(String token, String userId) {
        Intent intent = new Intent(this, MainActivity.class);
        Bundle extra = new Bundle();
        extra.putString(Constants.TOKEN_KEY, token);
        extra.putString(Constants.USER_ID, userId);
        intent.putExtra(Constants.TOKEN_USER_ID, extra);
        finish();
        startActivity(intent);
    }

    private boolean _isValidEmail(String email) {
        if (email == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }

    }

    private EditText _editTextName;
    private EditText _editTextEmail;
    private EditText _editTextPassword;
    private ConstraintLayout _constraintLayout;
}
