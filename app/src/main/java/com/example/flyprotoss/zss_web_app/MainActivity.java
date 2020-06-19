package com.example.flyprotoss.zss_web_app;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private WebView webView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView1 = findViewById(R.id.webview1);

        //不显示横向滚动条
        webView1.setHorizontalScrollBarEnabled(false);
        //不显示纵向滚动
        webView1.setVerticalScrollBarEnabled(false);
        //允许截图
        webView1.setDrawingCacheEnabled(true);
        //屏蔽长按事件
        webView1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

        //初始化WebSettings
        final WebSettings settings = webView1.getSettings();
        settings.setJavaScriptEnabled(true);
        final String ua = settings.getUserAgentString();
        settings.setUserAgentString(ua + " zss");
        //隐藏缩放控件
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        //禁止缩放
        settings.setSupportZoom(false);
        //文件权限
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setAllowContentAccess(true);
        //缓存相关
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        webView1.setWebChromeClient(new WebChromeClient());
        webView1.addJavascriptInterface(this,"ZSS");

//        webView1.loadUrl("https://www.baidu.com");
        webView1.loadUrl("file:///android_asset/" + "index.html");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sendToH5(0, new Date().toString());
            }
        },3000);
    }

    private void sendToH5(Integer type, String msg) {
        webView1.evaluateJavascript("sendToH5('{\"type\":" + type + "", \"msg\":\"" + msg + "\"}');",null);
    }

    @JavascriptInterface
    public void sendToNative(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
