package com.cdt.bombeachguide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by nguyen on 6/4/2016.
 */
public class WebViewActivity extends Activity {
    String url;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);

        url=getIntent().getStringExtra("link");
        final WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView .loadUrl(url);
        myWebView.setWebViewClient(new MyWebViewClient());
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(false);
        webSettings.setLightTouchEnabled(false);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                Document doc = null;
                try {
          //          doc = Jsoup.connect(url).get();
                    Connection connection=Jsoup.connect(url);
                    connection.timeout(10000);
                    doc=connection.get();

                Elements newsHeadlines = doc.select("div");

                for(final Element element:newsHeadlines){

                    if(element.attr("id").equals("mw-content-text"))
                    {
                        runOnUiThread(new  Runnable() {
                            public void run() {
                                myWebView.loadData(element.outerHtml(), "text/html", "UTF-8");
                            }
                        });



                    }

                }
//                    Elements newsHeadlines = doc.select("table");
//
//                    for(final Element element:newsHeadlines){
//
//                      //  if(element.attr("id").equals("mw-content-text"))
//                        {
//                            runOnUiThread(new  Runnable() {
//                                public void run() {
//                                    myWebView.loadData(element.outerHtml(), "text/html", "UTF-8");
//                                }
//                            });
//                        break;
//
//
//                        }
//
//                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });//.start();

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



