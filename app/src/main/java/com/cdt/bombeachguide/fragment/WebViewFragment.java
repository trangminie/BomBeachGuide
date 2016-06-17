package com.cdt.bombeachguide.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cdt.bombeachguide.R;

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
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.webview_fragment, container, false);

        final WebView myWebView = (WebView) rootView.findViewById(R.id.webview);
        myWebView .loadUrl(mUrl);
        myWebView.setWebViewClient(new MyWebViewClient());
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(false);
        webSettings.setLightTouchEnabled(false);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);












        setTitle(mTitle);
        return rootView;

    }
    private class MyWebViewClient extends WebViewClient {

        @Override

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            WebViewFragment webviewfragment = WebViewFragment.newInstance("vietduc",url);
            displayDetail(webviewfragment);
            return true;
        }
    }

}
