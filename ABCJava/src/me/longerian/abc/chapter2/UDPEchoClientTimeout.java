package me.longerian.abc.chapter2;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPEchoClientTimeout {

	private static final int TIMEOUT = 3000; // Resend timeout (milliseconds)
	private static final int MAXTRIES = 5; // Maximum retransmissions

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		InetAddress serverAddress = InetAddress.getByName("127.0.0.1"); // Server
																	// address
		// Convert the argument String to bytes using the default encoding
		byte[] bytesToSend = "hellohello".getBytes();

		int servPort = (args.length == 3) ? Integer.parseInt(args[2]) : 7;

		DatagramSocket socket = new DatagramSocket();

		socket.setSoTimeout(TIMEOUT); // Maximum receive blocking time
										// (milliseconds)

		DatagramPacket sendPacket = new DatagramPacket(bytesToSend, // Sending
																	// packet
				bytesToSend.length, serverAddress, servPort);

		DatagramPacket receivePacket = // Receiving packet
		new DatagramPacket(new byte[bytesToSend.length], bytesToSend.length);

		int tries = 0; // Packets may be lost, so we have to keep trying
		boolean receivedResponse = false;
		do {
			socket.send(sendPacket); // Send the echo string
			try {
				socket.receive(receivePacket); // Attempt echo reply reception

				if (!receivePacket.getAddress().equals(serverAddress)) {// Check
																		// source
					throw new IOException(
							"Received packet from an unknown source");
				}
				receivedResponse = true;
			} catch (InterruptedIOException e) { // We did not get anything
				tries += 1;
				System.out.println("Timed out, " + (MAXTRIES - tries)
						+ " more tries...");
			}
		} while ((!receivedResponse) && (tries < MAXTRIES));

		if (receivedResponse) {
			System.out.println("Received: "
					+ new String(receivePacket.getData()));
		} else {
			System.out.println("No response -- giving up.");
		}
		socket.close();
	}
}
