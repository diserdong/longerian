package me.longerian.abc.udp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UdpPoke {

	private int bufferSize;
	private DatagramSocket socket;
	private DatagramPacket outgoing;

	public UdpPoke(InetAddress host, int port, int bufferSize, int timeout)
			throws SocketException {
		outgoing = new DatagramPacket(new byte[1], 1, host, port);
		this.bufferSize = bufferSize;
		this.socket = new DatagramSocket(9001);
		this.socket.connect(host, port);
		this.socket.setSoTimeout(timeout);
	}

	public byte[] poke() {
		byte[] response = null;
		try {
			socket.send(outgoing);
			DatagramPacket incoming = new DatagramPacket(new byte[bufferSize],
					bufferSize);
			socket.receive(incoming);
			int numBytes = incoming.getLength();
			response = new byte[numBytes];
			System.arraycopy(incoming.getData(), 0, response, 0, numBytes);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return response;
	}

	public static void main(String[] args) {
		InetAddress host;
		int port = 13;
		try {
			host = InetAddress.getByName("127.0.0.1");
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return ;
		}
		try {
			UdpPoke poker = new UdpPoke(host, port, 8192, 30000);
			byte[] response = poker.poke();
			if(response == null) {
				System.out.println("no response");
				return ;
			}
			String result = "";
			try {
				result = new String(response, "ASCII");
			} catch(UnsupportedEncodingException ex) {
				ex.printStackTrace();
			}
			System.out.println(result);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
