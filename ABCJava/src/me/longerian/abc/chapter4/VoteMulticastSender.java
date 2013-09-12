package me.longerian.abc.chapter4;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

import me.longerian.abc.chapter3.vote.VoteMsg;
import me.longerian.abc.chapter3.vote.VoteMsgCoder;
import me.longerian.abc.chapter3.vote.VoteMsgTextCoder;

public class VoteMulticastSender {

	public static final int CANDIDATEID = 475;

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		InetAddress destAddr = InetAddress.getByName("224.0.0.1"); // Destination
		if (!destAddr.isMulticastAddress()) { // Test if multicast address
			throw new IllegalArgumentException("Not a multicast address");
		}

		int destPort = 9001; // Destination port
		int TTL = 3; // Set TTL

		MulticastSocket sock = new MulticastSocket();
		sock.setTimeToLive(TTL); // Set TTL for all datagrams

		VoteMsgCoder coder = new VoteMsgTextCoder();

		VoteMsg vote = new VoteMsg(true, true, CANDIDATEID, 1000001L);

		// Create and send a datagram
		byte[] msg = coder.toWire(vote);
		DatagramPacket message = new DatagramPacket(msg, msg.length, destAddr,
				destPort);
		System.out.println("Sending Text-Encoded Request (" + msg.length
				+ " bytes): ");
		System.out.println(vote);
		sock.send(message);

		sock.close();
	}

}
