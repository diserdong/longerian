package me.longerian.abc.https;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpsTest {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
//		CookieManager cmrCookieMan = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
//		CookieHandler.setDefault(cmrCookieMan);
		proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyAddress , proxyPort));
		List<String> baseCookie = baseInit();
		Thread.sleep(5000);
		List<String> loginCookie = baseLogin(baseCookie);
	}
	
	static String proxyAddress = "10.96.88.150";
	static int proxyPort = 8888;
	static Proxy proxy;
	
	public static List<String> baseInit() throws IOException {
		URL url = new URL("https://www.huobi.com/");
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		InputStream in = urlConnection.getInputStream();
//		copyInputStreamToOutputStream(in, System.out);
		List<String> cookies = new ArrayList<String>();
		Map<String, List<String>> headers = urlConnection.getHeaderFields();
		Iterator<String> itr = headers.keySet().iterator();
		while(itr.hasNext()) {
			String key = itr.next();
			List<String> values = headers.get(key);
			for(String value : values) {
				System.out.println(key + " -- " + value);
				if("Set-Cookie".equals(key)) {
					int indexOfSemicolon = value.indexOf(";");
					String cookie = value.substring(0, indexOfSemicolon);
					cookies.add(cookie);
				}
			}
		}
		System.out.println(cookies);
		System.out.println();
		return cookies;
	}
	
	public static List<String> baseLogin(List<String> baseCookie) throws IOException {
		String content = URLEncoder.encode("email=kennyhong@molo.cn&password=Ju1OR4vuBA8oE6Vu", "utf-8");
		URL url = new URL("https://www.huobi.com/account/login.php");
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setRequestMethod("POST");
//		urlConnection.setRequestProperty("Host", "www.huobi.com");
//		urlConnection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//		urlConnection.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
//		urlConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,zh-TW;q=0.4");
//		urlConnection.setRequestProperty("Cache-Control", "max-age=0");
//		urlConnection.setRequestProperty("Connection", "keep-alive");
//		urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//		urlConnection.setRequestProperty("Origin", "https://www.huobi.com");
//		urlConnection.setRequestProperty("Referer", "https://www.huobi.com/");
//		urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.76 Safari/537.36");
		urlConnection.setRequestProperty("Content-Length", content.getBytes().length + "");
		if(baseCookie != null) {
			StringBuilder sb = new StringBuilder();
			for(int i = 0, size = baseCookie.size(); i < size; i++) {
				sb.append(baseCookie.get(i));
				if(i < size - 1) {
					sb.append("; ");
				}
			}
			System.out.println(sb.toString());
			urlConnection.setRequestProperty("Cookie", sb.toString());
//			urlConnection.setRequestProperty("Cookie", "DS_ID=DG; HUOBIMEIBISESSID=default_68d8e1eb9f7641cf2978f75c3281a5b1");
		}
		urlConnection.setDoInput(true);
		urlConnection.setDoOutput(true);
		urlConnection.connect();
		DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());
		out.writeBytes(content); 
		out.flush();
		InputStream in = urlConnection.getInputStream();
//		copyInputStreamToOutputStream(in, System.out);
		List<String> cookies = new ArrayList<String>();
		Map<String, List<String>> headers = urlConnection.getHeaderFields();
		Iterator<String> itr = headers.keySet().iterator();
		while(itr.hasNext()) {
			String key = itr.next();
			List<String> values = headers.get(key);
			for(String value : values) {
				System.out.println(key + " -- " + value);
				if("Set-Cookie".equals(key)) {
					int indexOfSemicolon = value.indexOf(";");
					String cookie = value.substring(0, indexOfSemicolon);
					cookies.add(cookie);
				}
			}
		}
		System.out.println(cookies);
		System.out.println();
		return cookies;
	}
	
	public static void copyInputStreamToOutputStream(InputStream input, OutputStream output)
		    throws IOException
		{
		    byte[] buffer = new byte[1024]; // Adjust if you want
		    int bytesRead;
		    while ((bytesRead = input.read(buffer)) != -1)
		    {
		        output.write(buffer, 0, bytesRead);
		    }
		    System.out.println();
		}
	
}
