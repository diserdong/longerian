package me.longerian.abc.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class HttpsClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String host = "www.alipay.com";
		int port = 443;
		try {
			SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			SSLSocket socket = (SSLSocket) factory.createSocket(host, port);
			String[] supported = socket.getSupportedCipherSuites();
			for(String s : supported) {
				System.out.println(s);
			}
			System.out.println("==========");
			String[] enabled = socket.getEnabledCipherSuites();
			for(String s : enabled) {
				System.out.println(s);
			}
			System.out.println("==========");
			socket.setEnabledCipherSuites(enabled);
			Writer out = new OutputStreamWriter(socket.getOutputStream());
			out.write("GET http://" + host + "/ HTTP/1.1\r\n");
			out.write("Host: " + host + "\r\n");
			out.write("\r\n");
			out.flush();
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String s;
			while(!(s = in.readLine()).equals("")) {
				//read headers
				System.out.println(s);
			}
			System.out.println();
//			String contentLength = in.readLine();
//			int length = Integer.MAX_VALUE;
//			try {
//				length = Integer.parseInt(contentLength.trim(), 16);
//			} catch(NumberFormatException ex) {
//				ex.printStackTrace();
//			}
//			System.out.println(contentLength);
			int c;
			int i = 0;
			while((c = in.read()) != -1) {
				System.out.write(c);
			}
			System.out.println();
			out.close();
			in.close();
			socket.close();
		} catch(IOException ex) {
			
		}
				
	}

}
