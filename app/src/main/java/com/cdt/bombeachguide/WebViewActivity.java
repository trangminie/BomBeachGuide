package com.cdt.bombeachguide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by nguyen on 6/4/2016.
 */
public class WebViewActivity extends Activity {
    String url;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);
      //  Toast.makeText(getApplicationContext(),getIntent().getStringExtra("link"),Toast.LENGTH_LONG).show();
            url=getIntent().getStringExtra("link");
     //   String url2=new String("http://boombeach.wikia.com//wiki/Statue");

         //   Log.d("vietduc ",url);
          //  url="http://boombeach.wikia.com/wiki/Statue";
        final WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView .loadUrl(url);
        myWebView.setWebViewClient(new MyWebViewClient());
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(false);
        webSettings.setLightTouchEnabled(false);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
    }

    // Use When

    private class MyWebViewClient extends WebViewClient {

        @Override

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
            intent.putExtra("link",url);
            startActivity(intent);
            return true;
        }
    }
}



