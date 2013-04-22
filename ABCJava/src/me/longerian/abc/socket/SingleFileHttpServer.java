package me.longerian.abc.socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleFileHttpServer extends Thread {

	private byte[] content;
	private byte[] header;
	private int port = 80;
	
	public SingleFileHttpServer(byte[] data, String encodeing, String mimeType, int port) throws UnsupportedEncodingException {
		this.content = data;
		this.port = port;
		String header = "HTTP/1.1 200 OK\r\n"
				+ "Server: OneFile\r\n"
				+ "Content-Length: " + this.content.length + "\r\n"
				+ "Content-Type: " + mimeType + "\r\n"
				+ "\r\n";
		this.header = header.getBytes("ASCII");
	}

	@Override
	public void run() {
		try {
			ServerSocket server = new ServerSocket(port);
			System.out.println("accepting connections on port " + server.getLocalPort());
			System.out.println("data to be sent: ");
			System.out.write(content);
			while(true) {
				Socket connection = null;
				try {
					connection = server.accept();
					OutputStream out = new BufferedOutputStream(connection.getOutputStream());
					InputStream in = new BufferedInputStream(connection.getInputStream());
					StringBuffer request = new StringBuffer(80);
					while(true) {
						int c = in.read();
						if(c == '\r' || c == '\n' || c == -1) break;
						request.append((char)c);
					}
					if(request.toString().indexOf("HTTP/") != -1) {
						out.write(header);
					}
					out.write(content);
					out.flush();
				} catch (IOException e) {
					
				} finally {
					if(connection != null) connection.close();
				}
			}
		} catch (IOException e) {
			System.err.println("could not start server. port occupied");
		}
	}
	
	public static void main(String[] args) {
		try {
			String contentType = "text/plain";
			InputStream in = new FileInputStream("NOTICE");
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int b;
			while((b = in.read()) != -1) out.write(b);
			byte[] data = out.toByteArray();
			int port = 80;
			String encoding = "ASCII";
			Thread t = new SingleFileHttpServer(data, encoding, contentType, port);
			t.start();
		} catch(ArrayIndexOutOfBoundsException ex) {
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
