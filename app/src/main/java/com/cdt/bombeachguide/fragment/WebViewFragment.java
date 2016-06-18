package com.cdt.bombeachguide.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cdt.bombeachguide.R;
import com.cdt.bombeachguide.WebViewActivity;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Trang on 5/19/2016.
 */
public class WebViewFragment extends BaseFragment {

    public static String mTitle;
    public static String mUrl;

    public static WebViewFragment newInstance(String title, String url) {
        WebViewFragment fragment = new WebViewFragment();
        mTitle = title;
        mUrl = url;
    //   mUrl="http://boombeach.wikia.com/wiki/Statue";
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.webview_fragment, container, false);

        final WebView myWebView = (WebView) rootView.findViewById(R.id.webview);
      //  myWebView .loadUrl(mUrl);
        myWebView.setWebViewClient(new MyWebViewClient());
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(false);
        webSettings.setLightTouchEnabled(false);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        myWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        setTitle(mTitle);
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                Document doc = null;
                try {
                    //          doc = Jsoup.connect(url).get();
                    Connection connection= Jsoup.connect(mUrl);
                    connection.timeout(10000);
                    doc=connection.get();
                    Elements newsHeadlines = doc.select("div");

                    for(final Element element:newsHeadlines){

                        if(element.attr("id").equals("mw-content-text"))
                        {
                            getActivity().runOnUiThread(new  Runnable() {
                                public void run() {
                                    myWebView.loadDataWithBaseURL("http://boombeach.wikia.com/wiki/", element.outerHtml(), "text/html", "UTF-8", "");
                                }
                            });
                        }

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();
        return rootView;

    }
    private class MyWebViewClient extends WebViewClient {

        @Override

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("vietduc",url);
            String[] urlarray = url.split("/");

            WebViewFragment webviewfragment = WebViewFragment.newInstance(urlarray[urlarray.length-1],url);
            displayDetail(webviewfragment);
            return true;
        }

}}
