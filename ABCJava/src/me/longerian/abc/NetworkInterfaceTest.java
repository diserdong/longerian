package me.longerian.abc;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;

public class NetworkInterfaceTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			for(NetworkInterface ni : Collections.list(NetworkInterface.getNetworkInterfaces())) {
				System.out.println(ni);
			    for (InterfaceAddress ia : ni.getInterfaceAddresses()) {
			        System.out.println("\t" + ia);
			    }
			    System.out.println();
			}
			NetworkInterface ni = NetworkInterface.getByName("eth0");
			if(ni == null) {
				System.out.println("No such interface: eth0");
			} else {
				System.out.println(ni.getDisplayName() + "\t" + ni.getName() + "\t" + ni.getIndex());
			}
			
			InetAddress local = InetAddress.getByName("127.0.0.1");
			NetworkInterface ni1 = NetworkInterface.getByInetAddress(local);
			if(ni1 == null) {
				System.out.println("That's wired. No local loopback address.");
			} else {
				System.out.println(ni1.getDisplayName() + "\t" + ni1.getName() + "\t" + ni1.getIndex());
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
