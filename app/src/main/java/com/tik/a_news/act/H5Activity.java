package com.tik.a_news.act;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.tik.a_news.R;
import com.tik.a_news.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class H5Activity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.webview)
    WebView webView;

    @OnClick(R.id.tv_back)
    void back(){
        finish();
    }

    String url;

    @Override
    protected void beforeBindViews() {

    }

    @Override
    protected void afterBindViews() {
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
//                    Log.i("", "===========100");
                }
            }

        });
        webView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                webView.reload();
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                checkNetwork();
            }

            @Override
            public void onPageStarted(WebView view, String url1, Bitmap favicon) {
                super.onPageStarted(view, url1, favicon);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
//                checkNetwork();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //调用拨号程序
                if (url.startsWith("mailto:") || url.startsWith("geo:") || url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        Bundle bundle = getIntent().getExtras();
        url = bundle.getString("url");
        Log.e("WebViewUrl", url);
//        final String title = bundle.getString("title");
//        tvTitle.setText(title);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
