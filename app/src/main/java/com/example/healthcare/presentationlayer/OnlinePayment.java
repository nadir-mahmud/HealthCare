package com.example.healthcare.presentationlayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.healthcare.R;

public class OnlinePayment extends AppCompatActivity {

    WebView payemtWebview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_payment);
        payemtWebview = findViewById(R.id.webviewpayment);
        WebSettings webSettings = payemtWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        payemtWebview.setWebViewClient(new WebViewClient());
        payemtWebview.loadUrl("https://unobeyed-bell.000webhostapp.com/");

        payemtWebview.setWebViewClient(new
           WebViewClient() {
               public void onPageFinished(WebView view, String url) {

                   // TODO Auto-generated method stub
                   super.onPageFinished(view, url);

                   Log.e("Finished Url :", "" + url);

               }
           });

    }
    public void onBackPressed()
    {

        if(payemtWebview.canGoBack())
        {
            payemtWebview.goBack();

        }
        else{
            super.onBackPressed();
        }
    }

}