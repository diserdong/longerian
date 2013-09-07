package me.longerian.abc.chapter2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPEchoServer {

	private static final int ECHOMAX = 5; // Maximum size of echo datagram

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		int servPort = 7;

		DatagramSocket socket = new DatagramSocket(servPort);
		DatagramPacket packet = new DatagramPacket(new byte[ECHOMAX], ECHOMAX);

		while (true) { // Run forever, receiving and echoing datagrams
			socket.receive(packet); // Receive packet from client
			System.out.println("Handling client at "
					+ packet.getAddress().getHostAddress() + " on port "
					+ packet.getPort());
			socket.send(packet); // Send the same packet back to client
			packet.setLength(ECHOMAX); // Reset length to avoid shrinking buffer
		}
	}

}
