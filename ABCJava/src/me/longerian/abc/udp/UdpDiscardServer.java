package me.longerian.abc.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpDiscardServer {

	public static final int DEFAULT_PORT = 9;
	public static final int MAX_PACKET_SIZE = 65507;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int port = DEFAULT_PORT;
		byte[] buffer = new byte[MAX_PACKET_SIZE];
		try {
			DatagramSocket server = new DatagramSocket(port);
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			while(true) {
				try {
					server.receive(packet);
					String s = new String(packet.getData(), 0, packet.getLength(), "UTF-8");
					System.out.println(packet.getAddress() + " at port " + packet.getPort() + " says " + s);
					packet.setLength(buffer.length);
				} catch(IOException ex) {
					ex.printStackTrace();
				}
			}
		} catch(SocketException ex) {
			ex.printStackTrace();
		}
	}

}
