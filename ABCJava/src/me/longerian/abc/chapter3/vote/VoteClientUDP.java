package me.longerian.abc.chapter3.vote;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class VoteClientUDP {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		InetAddress destAddr = InetAddress.getByName("127.0.0.1"); // Destination
																// addr
		int destPort = 9001; // Destination port
		int candidate = 888; // 0 <= candidate <= 1000
													// req'd

		DatagramSocket sock = new DatagramSocket(); // UDP socket for sending
		sock.connect(destAddr, destPort);

		// Create a voting message (2nd param false = vote)
		VoteMsg vote = new VoteMsg(false, false, candidate, 0);

		// Change Text to Bin here for a different coding strategy
		VoteMsgCoder coder = new VoteMsgTextCoder();

		// Send request
		byte[] encodedVote = coder.toWire(vote);
		System.out.println("Sending Text-Encoded Request ("
				+ encodedVote.length + " bytes): ");
		System.out.println(vote);
		DatagramPacket message = new DatagramPacket(encodedVote,
				encodedVote.length);
		sock.send(message);
		// Receive response
		message = new DatagramPacket(
				new byte[VoteMsgTextCoder.MAX_WIRE_LENGTH],
				VoteMsgTextCoder.MAX_WIRE_LENGTH);
		sock.receive(message);
		encodedVote = Arrays.copyOfRange(message.getData(), 0,
				message.getLength());

		System.out.println("Received Text-Encoded Response ("
				+ encodedVote.length + " bytes): ");
		vote = coder.fromWire(encodedVote);
		System.out.println(vote);
	}

}
