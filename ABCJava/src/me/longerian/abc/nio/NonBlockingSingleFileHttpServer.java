package me.longerian.abc.nio;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import me.longerian.abc.socket.SingleFileHttpServer;

public class NonBlockingSingleFileHttpServer {
	
	private ByteBuffer contentBuffer;
	private int port = 80;

	public NonBlockingSingleFileHttpServer(ByteBuffer data, String encoding, String mimeType, int port) throws UnsupportedEncodingException {
		this.port = port;
		String header = "HTTP/1.1 200 OK\r\n"
				+ "Server: OneFile\r\n"
				+ "Content-Length: " + data.limit() + "\r\n"
				+ "Content-Type: " + mimeType + "\r\n"
				+ "\r\n";
		byte[] headerData = header.getBytes("ASCII");
		ByteBuffer buffer = ByteBuffer.allocate(data.limit() + headerData.length);
		buffer.put(headerData);
		buffer.put(data);
		buffer.flip();
		this.contentBuffer = buffer;
	}
	
	public void run() throws IOException {
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		ServerSocket serverSocket = serverChannel.socket();
		Selector selector = Selector.open();
		InetSocketAddress localPort = new InetSocketAddress(port);
		serverSocket.bind(localPort);
		serverChannel.configureBlocking(false);
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		while(true) {
			selector.select();
			Iterator keys = selector.selectedKeys().iterator();
			while(keys.hasNext()) {
				SelectionKey key = (SelectionKey) keys.next();
				keys.remove();
				try {
					if(key.isAcceptable()) {
						ServerSocketChannel server = (ServerSocketChannel) key.channel();
						SocketChannel channel = server.accept();
						channel.configureBlocking(false);
						SelectionKey newKey = channel.register(selector, SelectionKey.OP_READ);
					} else if(key.isWritable()) {
						SocketChannel channel = (SocketChannel) key.channel();
						ByteBuffer buffer = (ByteBuffer) key.attachment();
						if(buffer.hasRemaining()) {
							channel.write(buffer);
						} else {
							channel.close();
						}
					} else if(key.isReadable()) {
						SocketChannel channel = (SocketChannel) key.channel();
						ByteBuffer buffer = ByteBuffer.allocate(4096);
						channel.read(buffer);
						key.interestOps(SelectionKey.OP_WRITE);
						key.attach(contentBuffer.duplicate());
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
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String contentType = "text/plain";
			FileInputStream fin = new FileInputStream("NOTICE");
			FileChannel in = fin.getChannel();
			ByteBuffer input = in.map(FileChannel.MapMode.READ_ONLY, 0, in.size());
			int port = 80;
			String encoding = "ASCII";
			NonBlockingSingleFileHttpServer server = new NonBlockingSingleFileHttpServer(input, encoding, contentType, port);
			server.run();
		} catch(ArrayIndexOutOfBoundsException ex) {
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
