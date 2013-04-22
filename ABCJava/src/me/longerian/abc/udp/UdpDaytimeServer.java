package me.longerian.abc.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;

public class UdpDaytimeServer {

	public final static int DEFAULT_PORT = 13;
	public final static int MAX_PACKET_SIZE = 65507;

	public static void main(String[] args) {
		int port = DEFAULT_PORT;
		byte[] buf = new byte[MAX_PACKET_SIZE];
		try {
			DatagramSocket server = new DatagramSocket(DEFAULT_PORT);
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			while(true) {
				try {
					server.receive(packet);
					InetAddress clientAddress = packet.getAddress();
					int clientPort = packet.getPort();
					System.out.println("receive from " + clientAddress + ":" + clientPort);
					Date now = new Date();
					byte[] time = now.toString().getBytes();
					DatagramPacket timePacket = new DatagramPacket(time, time.length, clientAddress, clientPort);
					server.send(timePacket);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

}
