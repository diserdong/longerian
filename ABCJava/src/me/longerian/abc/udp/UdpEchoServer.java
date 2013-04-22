package me.longerian.abc.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;

public class UdpEchoServer extends UdpServer {

	public final static int DEFAULT_PORT = 7;

	public UdpEchoServer() throws SocketException {
		super(DEFAULT_PORT);
	}

	@Override
	public void respond(DatagramPacket request) {
		try {
			DatagramPacket outgoing = new DatagramPacket(request.getData(),
					request.getLength(), request.getAddress(),
					request.getPort());
			socket.send(outgoing);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			UdpServer server = new UdpEchoServer();
			server.start();
		} catch (SocketException ex) {
			ex.printStackTrace();
		}
	}

}
