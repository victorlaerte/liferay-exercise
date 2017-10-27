package com.example.luisafarias.myapplication.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.luisafarias.myapplication.R;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        WebView wv = (WebView) findViewById(R.id.webView);

        WebSettings ws = wv.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setSupportZoom(false);
        wv.loadUrl("http://paranaportal.uol.com.br/operacao-lava-jato/recibos-novos-e-originais-sao-apresentados-pela-defesa-de-lula/");
    }
}
