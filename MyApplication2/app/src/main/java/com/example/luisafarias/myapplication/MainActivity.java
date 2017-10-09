package com.example.luisafarias.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.example.luisafarias.myapplication.util.Constants;
import com.wedeploy.android.Callback;
import com.wedeploy.android.WeDeploy;
import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.auth.TokenAuthorization;
import com.wedeploy.android.transport.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import com.example.luisafarias.myapplication.model.Feed;
import com.example.luisafarias.myapplication.model.FeedListAdapter;
import com.example.luisafarias.myapplication.model.Repositorio;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = (ConstraintLayout) findViewById(R.id.container);

        Toolbar myToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        FeedListFragment feedListFragment = new FeedListFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
//        ft.replace(R.id.frame_layout_fragment,feedListFragment, "test");
        ft.add(R.id.frame_layout_fragment,feedListFragment, "test");
        ft.commit();

        _token = getIntent().getExtras().getString("tokenKey");
        _userId = getIntent().getExtras().getString("userID");

        Bundle bundle = new Bundle();
        bundle.putString("tokenKey",_token);
        feedListFragment.setArguments(bundle);


        if (_token == null) {
            throw new IllegalArgumentException();
        }

        _authorization = new TokenAuthorization(_token);
        //_allFeeds = (ListView) findViewById(R.id.lista_feed);

        //reloadFeeds();

        /**onClick get feed from newFeedFragment **/

        Button actionSaveFeed = (Button)findViewById(R.id.saveFeed);
        actionSaveFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newUrlName = (EditText) findViewById(R.id.newNameFeed);
                EditText newUrl = (EditText) findViewById(R.id.newUrlFeed);

                String name = newUrlName.getText().toString();
                String url = newUrl.getText().toString();

                Feed feed = new Feed(name,url,_userId);
                Log.d("mainActivity",feed.get_nome());
                try {
                    saveFeed(feed);
                } catch (JSONException e) {
                    Log.e(MainActivity.class.getName(),e.getMessage());
                }
            }
        });

    }

//    private void reloadFeeds() {
//        final Context context = this;
//        Repositorio.getInstance()
//            .feedListAll(_authorization, new Repositorio.CallbackFeeds() {
//            @Override
//            public void onSuccess(List<Feed> feedList) {
//                _feedAdapter = new FeedListAdapter(context, _authorization, feedList);
//                _allFeeds.setAdapter(_feedAdapter);
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                Log.e(MainActivity.class.getName(),e.getMessage());
//            }
//        });
//    }

    public void goAddUrl(final View view){

        _weDeploy.auth(Constants.AUTH_URL)
                .authorization(_authorization)
                .getCurrentUser()
                .execute(new Callback() {
                    public void onSuccess(Response response) {

                        //TODO Verificar se usuário já foi passado antes para evitar essa request
                        //ele nao retorna o userId
                        JSONObject jsonBody;
                        try {
                            jsonBody = new JSONObject(response.getBody());
                            _userId = jsonBody.getString("id");
                            Log.d(MainActivity.class.getName(), _userId);
                        } catch (JSONException e) {
                            Log.e(MainActivity.class.getName(),e.getMessage());
                            e.printStackTrace();
                        }

                        final Intent intent =
                            new Intent(
                                MainActivity.this, NewUrlActivity.class);


                        intent.putExtra("userId", _userId);
                        intent.putExtra("token", _token);
                        startActivityForResult(intent, ACCESS_RESULT_NEW_FEED);
                    }

                    public void onFailure(Exception e) {
                        Snackbar snackbar = Snackbar.make(view,e.getMessage(),Snackbar.LENGTH_LONG);
                        snackbar.show();
                        Log.e(MainActivity.class.getName(),e.getMessage());
                    }
                });

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_feed,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){

        final Intent intent = new Intent(this, LoginActivity.class);
        int id = item.getItemId();

        if(id == R.id.logout) {
            _weDeploy.auth(Constants.AUTH_URL)
                .authorization(_authorization)
                .signOut()
                .execute(new Callback() {
                    public void onSuccess(Response response) {
                        Log.d(MainActivity.class.getName(), "saiu");
                        finish();
                        startActivity(intent);
                    }

                    public void onFailure(Exception e) {
                        Log.e(MainActivity.class.getName(), e.getMessage());
                    }
                });
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, final Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == ACCESS_RESULT_NEW_FEED) {
            if(intent!=null){
                Feed feed = intent.getExtras().getParcelable("feed");
                try {
                        Repositorio.getInstance()
                            .addFeed(feed, _authorization, new Repositorio.CallbackFeed() {
                        @Override
                        public void onSuccess(Feed feed) {
                            //reloadFeeds();
                            Log.d(MainActivity.class.getName(),"salvou");
                            Snackbar.make(container,"Salvou",Snackbar.LENGTH_LONG).show();

                        }

                        @Override
                        public void onFailure(Exception e) {
                            Log.e(MainActivity.class.getName(),e.getMessage());
                            Snackbar.make(container,e.getMessage(),Snackbar.LENGTH_LONG).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void goAddNewFeed(View view){
        Fragment fragment = new NewFeedFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_fragment,fragment);
        fragmentTransaction.addToBackStack(null);//quando apertar botao de voltar ele voltar para o fragment anterior
        fragmentTransaction.commit();

    }

    public void saveFeed(Feed feed) throws JSONException {
        Repositorio.getInstance().addFeed(feed,_authorization, new Repositorio.CallbackFeed() {

            @Override
            public void onSuccess(Feed feed) {
                Log.d(MainActivity.class.getName(),"salvou");
                Snackbar.make(container,"Salvou",Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Exception e) {
                Log.e(MainActivity.class.getName(),e.getMessage());
                Snackbar.make(container,e.getMessage(),Snackbar.LENGTH_LONG).show();
            }
        });
    }



    //private ListView _allFeeds;
    private Authorization _authorization;
    private Feed _feed;
    private WeDeploy _weDeploy = new WeDeploy.Builder().build();
    private String _userId;
    private String _token;
    //private FeedListAdapter _feedAdapter;
    private final int ACCESS_RESULT_NEW_FEED = 1234;

}
