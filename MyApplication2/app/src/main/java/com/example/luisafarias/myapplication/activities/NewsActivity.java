package com.example.luisafarias.myapplication.activities;

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

        WebView wv = findViewById(R.id.webView);

        WebSettings ws = wv.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setSupportZoom(false);
        wv.loadUrl(_link);
    }

    private String _link;
}
