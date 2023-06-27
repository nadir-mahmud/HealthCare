package com.example.healthcare.presentationlayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.example.healthcare.R;

public class Location extends AppCompatActivity {

    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        mWebView = new WebView(this);
        mWebView.loadUrl("https://goo.gl/maps/FYeSrGtiLgkQfE7M7");
        setContentView(mWebView);
    }
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}