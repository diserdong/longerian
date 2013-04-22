package me.longerian.abc.udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UdpDiscardClient {

	public static final int DEFAULT_PORT = 9;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int port = DEFAULT_PORT;
		String hostname = "127.0.0.1";
		try {
			InetAddress server = InetAddress.getByName(hostname);
			BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
			DatagramSocket theSocket = new DatagramSocket();
			while(true) {
				String theLine = userInput.readLine();
				if (theLine.equals(".")) break;
				byte[] data = theLine.getBytes("UTF-8");
				DatagramPacket theOuput = new DatagramPacket(data, data.length, server, port);
				theSocket.send(theOuput);
			}
		} catch(UnknownHostException uhex) {
			uhex.printStackTrace();
		} catch(SocketException sex) {
			sex.printStackTrace();
		} catch(IOException ioex) {
			ioex.printStackTrace();
		}
	}

}
