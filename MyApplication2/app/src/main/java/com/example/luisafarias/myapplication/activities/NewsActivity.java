package com.example.luisafarias.myapplication.activities;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.luisafarias.myapplication.R;
import com.example.luisafarias.myapplication.util.Constants;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getIntent().getBundleExtra(Constants.LINK);
        _link = data.getString(Constants.LINK);

        setContentView(R.layout.activity_news);

        _wv = findViewById(R.id.webView);

        WebSettings ws = _wv.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setSupportZoom(false);
        _wv.loadUrl(_link);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        _wv.restoreState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        _wv.saveState(outState);
    }

    private String _link;
    private WebView _wv;
}
