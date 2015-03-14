package me.longerian.abcandroid.dynamicwebview;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import me.longerian.abcandroid.R;

/**
 * Created by huifeng.hxl on 2014/12/3.
 */
public class DynamicWebViewActivity extends Activity {

    Button changeUrl;
    WebView webView;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_webview);
        DisplayMetrics dm =getResources().getDisplayMetrics();
        int wscreen = dm.widthPixels;
        int hscreen = dm.heightPixels;
        Log.d("Longer", "window size: " + wscreen + " " + hscreen);

        changeUrl = (Button) findViewById(R.id.changeurl);
        changeUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Longer", "last content size: " + webView.getContentHeight());
                String url = "";
                switch (count) {
                    case 0:
                        url = "http://gtms02.alicdn.com/tps/i2/TB19xOvGVXXXXaNXpXXgalVIXXX-810-480.jpg_q90.jpg";
                        break;
                    case 1:
                        url = "http://www.baidu.com/";
                        break;
                    case 2:
                        url = "http://gi3.md.alicdn.com/imgextra/i3/2048184185/T24c9dXLxXXXXXXXXX_!!2048184185.jpg_430x430q90.jpg";
                        break;
                }
                count++;
                webView.loadUrl(url);
            }
        });

        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.d("Longer", "on progress changed " + newProgress);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("Longer", "on page finished: " + url);
                Log.d("Longer", "view size: " + webView.getWidth() + " " + view.getHeight());
                Log.d("Longer", "content size: " + webView.getContentHeight());
            }
        });
//        webView.loadUrl("http://m.tmall.com/");
        webView.loadUrl("http://gi3.md.alicdn.com/imgextra/i3/2048184185/T24c9dXLxXXXXXXXXX_!!2048184185.jpg_430x430q90.jpg");
    }
}
