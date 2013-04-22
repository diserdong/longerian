package me.longerian.abc.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public abstract class UdpServer extends Thread {

	private int bufferSize;
	protected DatagramSocket socket;
	
	public UdpServer(int port, int bufferSize) throws SocketException {
		this.bufferSize = bufferSize;
		this.socket = new DatagramSocket(port);
	}
	
	public UdpServer(int port) throws SocketException {
		this(port, 8192);
	}
	
	@Override
	public void run() {
		byte[] buffer = new byte[bufferSize];
		while(true) {
			DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
			try {
				socket.receive(incoming);
				this.respond(incoming);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public abstract void respond(DatagramPacket request);
	
}
