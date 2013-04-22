package me.longerian.abc.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class HighPortScanner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String host = "localhost";
		try {
			InetAddress theAddress = InetAddress.getByName(host);
			for(int i = 1024; i < 65536; i++) {
				Socket theSocket = null;
				try {
					 theSocket = new Socket(theAddress, i);
					System.out.println("There is a server on port " + i + " of " + host);
				} catch (IOException e) {
//					e.printStackTrace();
				} finally {
					try {
						if(theSocket != null) {
							theSocket.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
