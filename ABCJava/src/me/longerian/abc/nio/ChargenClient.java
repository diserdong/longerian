package me.longerian.abc.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

public class ChargenClient {

	public static int DEFAULT_PORT = 19;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int port = DEFAULT_PORT;
		try {
			SocketAddress address = new InetSocketAddress("rama.poly.edu", port);
			SocketChannel client = SocketChannel.open(address);
			
			ByteBuffer buffer = ByteBuffer.allocate(74);
			WritableByteChannel out = Channels.newChannel(System.out);
			while(client.read(buffer) != -1) {
				buffer.flip();
				out.write(buffer);
				buffer.clear();
			}
		} catch(IOException ex) {
			
		}
	}

}
