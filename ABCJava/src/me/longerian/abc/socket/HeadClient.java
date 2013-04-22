package me.longerian.abc.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;

public class HeadClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Socket theSocket = new Socket("www.baidu.com", 80);
			InputStream is = theSocket.getInputStream();
			OutputStream os = theSocket.getOutputStream();
			Writer osw = new OutputStreamWriter(os);
			osw.write("HEAD http://www.baidu.com/ HTTP/1.1\r\n");
			osw.write("\r\n");
			osw.flush();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String response = null;
			while((response = br.readLine()) != null) {
				System.out.println(response);
			}
			theSocket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
