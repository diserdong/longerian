package me.longerian.abc.udp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class UdpDiscardServerWithChannels {

	public final static int DEFAULT_PORT = 9;
	public final static int MAX_PACKET_SIZE = 65507;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			DatagramChannel channel = DatagramChannel.open();
			DatagramSocket socket = channel.socket();
			SocketAddress address = new InetSocketAddress(DEFAULT_PORT);
			socket.bind(address);
			ByteBuffer buffer = ByteBuffer.allocateDirect(MAX_PACKET_SIZE);
			while(true) {
				SocketAddress client = channel.receive(buffer);
				buffer.flip();
				System.out.println(client + " says ");
				while(buffer.hasRemaining()) {
					System.out.write(buffer.get());
				}
				System.out.println();
				buffer.clear();
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}

}
