package me.longerian.abc.udp;

import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UdpTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "this is a test";
		try {
			byte[] data = s.getBytes("ASCII");
			InetAddress ia = InetAddress.getByName("127.0.0.1");
			int port = 7;
			DatagramPacket dp = new DatagramPacket(data, data.length, ia, port);
			System.out.println("this packet is addressed to " + dp.getAddress() + " on port " + dp.getPort());
			System.out.println("there are " + dp.getLength() + " bytes of data in the packet");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
