package me.longerian.abc.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class EchoServer {

	public static int DEFAULT_PORT = 7;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int port = DEFAULT_PORT;
		System.out.println("listening for connections on port " + port);
		ServerSocketChannel serverChannel;
		Selector selector;
		try {
			serverChannel = ServerSocketChannel.open();
			ServerSocket ss = serverChannel.socket();
			InetSocketAddress address = new InetSocketAddress(port);
			ss.bind(address);
			serverChannel.configureBlocking(false);
			selector = Selector.open();
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch(IOException ex) {
			ex.printStackTrace();
			return ;
		}
		while(true) {
			try {
				selector.select();
			} catch(IOException ex) {
				ex.printStackTrace();
				break;
			}
			Set readyKeys = selector.selectedKeys();
			Iterator iterator = readyKeys.iterator();
			while(iterator.hasNext()) {
				SelectionKey key = (SelectionKey) iterator.next();
				iterator.remove();
				try {
					if(key.isAcceptable()) {
						ServerSocketChannel server = (ServerSocketChannel) key.channel();
						SocketChannel client = server.accept();
						System.out.println("accepted connection from " + client);
						client.configureBlocking(false);
						SelectionKey clientKey = client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ);
						ByteBuffer buffer = ByteBuffer.allocate(100);
						clientKey.attach(buffer);
					} else if(key.isReadable()) {
						System.out.println("read to buffer");
						SocketChannel client = (SocketChannel) key.channel();
						ByteBuffer output = (ByteBuffer) key.attachment();
						client.read(output);
					} else if(key.isWritable()) {
//						System.out.println("write buffer");
						SocketChannel client = (SocketChannel) key.channel();
						ByteBuffer output = (ByteBuffer) key.attachment();
						output.flip();
						client.write(output);
						output.clear();
//						output.compact();
					}
				} catch(IOException ex) {
					key.cancel();
					try {
						key.channel().close();
					} catch(IOException ioex) {
						
					}
				}
			}
		}
	}

}
