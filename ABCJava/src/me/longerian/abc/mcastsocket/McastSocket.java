package me.longerian.abc.mcastsocket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class McastSocket {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MulticastSocket ms = new MulticastSocket(4000);
			InetAddress ia = InetAddress.getByName("224.2.2.2");
			ms.joinGroup(ia);
			byte[] buffer = new byte[8192];
			while(true) {
				DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
				ms.receive(dp);
				String s = new String(dp.getData(), "8859_1");
				System.out.println(s);
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}

}
