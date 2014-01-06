package me.longerian.abc.socket;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Date;

public class DaytimeServer {

	public final static int DEFAULT_PORT = 13;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int port = DEFAULT_PORT;
		try {
			ServerSocket server = new ServerSocket(port);
			Socket connection = null;
			while(true) {
				try {
					connection = server.accept();
					int connectionPort = connection.getPort();
					p("remote port " + connectionPort);
					int localPort = connection.getLocalPort();
					p("local port " + localPort);
					InetAddress localAddress = connection.getLocalAddress();
					p("local address " + localAddress);
					SocketAddress localSockAddress = connection.getLocalSocketAddress();
					p("local sock address " + localSockAddress);
					InetAddress remoteAddress = connection.getInetAddress();
					p("remote address " + remoteAddress);
					SocketAddress remoteSockAddress = connection.getRemoteSocketAddress();
					p("remote sock address " + remoteSockAddress);
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
					Date now = new Date();
					bw.write(now.toString());
					bw.write("\r\n");
					bw.flush();
					connection.close();
				} catch (Exception e) {
				} finally {
					try {
						if(connection != null) {
							connection.close();
						}
					} catch (IOException ex) {}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void p(String str) {
		System.out.println(str);
	}

}
