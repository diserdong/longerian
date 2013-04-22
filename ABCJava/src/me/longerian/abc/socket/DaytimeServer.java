package me.longerian.abc.socket;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
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

}
