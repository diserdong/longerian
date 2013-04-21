package me.longerian.abcandroid;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.webkit.URLUtil;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        URI url;
//		try {
//			url = new URI("http://www.36kr.com:80");
//			System.out.println(url.getHost());
//		} catch (URISyntaxException e) {
//			e.printStackTrace();
//		}
        
//		if(URLUtil.isValidUrl("http://coolshell.cn")) {
//			Log.d("logner", "valid url: coolshell.cn");
//		} else {
//			Log.d("logner", "invalid url: coolshell.cn");;
//		}
//		if(URLUtil.isValidUrl("http://127.0.0.1")) {
//			Log.d("logner", "valid url: 127.0.0.1");
//		} else {
//			Log.d("logner", "invalid url: 127.0.0.1");
//		}
        
        StringBuilder sb = new StringBuilder();
        sb.append("GET http://coolshell.cn/ HTTP/1.1").append("\r\n")
        	.append("Accept: text/html, application/xhtml+xml, */*").append("\r\n")
        	.append("Accept-Language: zh-CN").append("\r\n")
        	.append("User-Agent: Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0)").append("\r\n")
        	.append("UA-CPU: AMD64").append("\r\n")
        	.append("Accept-Encoding: gzip, deflate").append("\r\n")
        	.append("Host: coolshell.cn").append("\r\n")
        	.append("Connection: Keep-Alive").append("\r\n");
        Log.d("longer", sb.toString());
        InputStream srcIS =  new ByteArrayInputStream(sb.toString().getBytes());
        replaceHost(srcIS, "http://coolshell.cn", "http://127.0.0.1");
        Log.d("longer", Environment.getDataDirectory().getAbsolutePath());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    
    public static InputStream replaceHost(InputStream is, String srcUrl, String dstUrl) {
    	if(is == null || !URLUtil.isValidUrl(srcUrl) || !URLUtil.isValidUrl(dstUrl)) {
			return null;
		}
    	String srcHost = Uri.parse(srcUrl).getHost();
    	Log.d("longer", "src:" + srcHost);
    	String dstHost = Uri.parse(dstUrl).getHost();
    	Log.d("longer", "dst:" + dstHost);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String t;
		try {
			while((t = br.readLine()) != null) {
				if(t.contains(srcHost)) {
					sb.append(t.replace(srcHost, dstHost)).append("\r\n");
				} else {
					sb.append(t).append("\r\n");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Log.d("longer", sb.toString());
		return new ByteArrayInputStream(sb.toString().getBytes());
	}
    
}
