package com.example.luisafarias.myapplication.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.luisafarias.myapplication.R;
import com.example.luisafarias.myapplication.fragments.NewRssFragment;
import com.example.luisafarias.myapplication.fragments.RssListFragment;
import com.example.luisafarias.myapplication.model.Rss;
import com.example.luisafarias.myapplication.model.WeDeployActions;
import com.example.luisafarias.myapplication.util.Constants;
import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.auth.TokenAuthorization;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _constraintLayout = findViewById(R.id.container);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        Bundle data = getIntent().getBundleExtra(Constants.TOKEN_USER_ID);
        _token = data.getString(Constants.TOKEN_KEY);
        _userId = data.getString(Constants.USER_ID);
        _authorization = new TokenAuthorization(_token);

        if (data == null) {
            throw new IllegalArgumentException();
        }


        if (_userId != null) Log.d("mainReceivedUserID", _userId);

        if (savedInstanceState == null) {

            RssListFragment rssListFragment = new RssListFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.frame_layout_fragment, rssListFragment,
                    Constants.GET_RSS_LIST_FRAGMENT);
            ft.commit();

            Bundle bundle = new Bundle();
            bundle.putString(Constants.TOKEN_KEY, _token);
            rssListFragment.setArguments(bundle);
        }

        _fAButton = findViewById(R.id.addRssFrag);

    }

    public boolean onOptionsItemSelected(MenuItem item) {

        final Intent intent = new Intent(this, LoginActivity.class);
        int id = item.getItemId();

        if (id == R.id.logout) {
            logout(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout(final Intent intent) {
        WeDeployActions.getInstance()
                .logoutUser(_token, new WeDeployActions.CallbackLogoutUser() {
                    @Override
                    public void onSuccess() {
                        SharedPreferences sharedPref =
                                getSharedPreferences(Constants.USER, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.clear();
                        editor.apply();
                        Log.d(MainActivity.class.getName(), "saiu");
                        finish();
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.e("MainActivity", e.getMessage());
                    }
                });
    }

    public void goAddNewRss(View view) {

        Bundle bundle = new Bundle();
        bundle.putString(Constants.TOKEN, _token);
        bundle.putString(Constants.USER_ID, _userId);
        bundle.putBoolean(Constants.NEW_OR_EDIT, false);

        Fragment fragment = new NewRssFragment();
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_fragment, fragment);
        fragmentTransaction.addToBackStack(null);//quando apertar botao de voltar ele volta para o fragment anterior
        fragmentTransaction.commit();
    }

    public void hideButton() {

        _fAButton.hide();
    }

    public void showButton() {
        if (_fAButton.getVisibility() != View.VISIBLE) {
            _fAButton.show();
        }
    }

    private Authorization _authorization;
    CoordinatorLayout _constraintLayout;
    FloatingActionButton _fAButton;
    private String _userId;
    private String _token;
}
