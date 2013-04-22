package me.longerian.abc.socket;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class SocketTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		SocketAddress proxyAddress = new InetSocketAddress("127.0.0.1", 8888);
//		Proxy proxy = new Proxy(Proxy.Type.SOCKS, proxyAddress);
//		Socket s = new Socket(proxy);
//		SocketAddress remote = new InetSocketAddress("http://www.baidu.com/", 80);
//		try {
//			s.connect(remote);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		try {
			Socket theSocket = new Socket("www.baidu.com", 80);
			int port = theSocket.getPort();
			p("remote port " + port);
			int localPort = theSocket.getLocalPort();
			p("local port " + localPort);
			InetAddress localAddress = theSocket.getLocalAddress();
			p("local address " + localAddress);
			SocketAddress localSockAddress = theSocket.getLocalSocketAddress();
			p("local sock address " + localSockAddress);
			InetAddress remoteAddress = theSocket.getInetAddress();
			p("remote address " + remoteAddress);
			SocketAddress remoteSockAddress = theSocket.getRemoteSocketAddress();
			p("remote sock address " + remoteSockAddress);
			boolean isConnected = theSocket.isConnected();
			p("is connected? " + isConnected);
			OutputStream os = theSocket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			osw.write("GET http://www.baidu.com/ HTTP/1.1\r\n");
			osw.write("User-Agent: Java/1.7.0_17\r\n");
			osw.write("Host: www.baidu.com\r\n");
			osw.write("Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2\r\n");
			osw.write("\r\n");
			osw.flush();
			InputStream is = theSocket.getInputStream();
			int c;
			while((c = is.read()) != -1) {
				System.out.write(c);
			}
			is.close();
			os.close();
			osw.close();
			theSocket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void p(String str) {
		System.out.println(str);
	}

}
