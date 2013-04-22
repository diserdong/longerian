package me.longerian.abc;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			InetAddress address = InetAddress.getByName("www.baidu.com");
			System.out.println(address.isAnyLocalAddress());
			System.out.println(address.isLinkLocalAddress());
			p(address.isLoopbackAddress() + "");
			p(address.isReachable(10) + "");
			System.out.println(address.getHostAddress());
			System.out.println(address.getHostName());
			byte[] addresspart = address.getAddress();
			for(byte p : addresspart) {
				int unsignedByte = p < 0 ? p + 256 : p;
				p(unsignedByte + "");
			}
			System.out.println(address);
			
			InetAddress[] addresses = InetAddress.getAllByName("google.com");
			for(InetAddress ia : addresses) {
				p(ia.toString());
			}
			
			InetAddress localhost = InetAddress.getLocalHost();
			p(localhost.getHostAddress());
			p(localhost.getHostName());
			p(localhost.isLoopbackAddress() + "");
			localhost = InetAddress.getByName("127.0.0.1");
			p(localhost.getHostAddress());
			p(localhost.getHostName());
			p(localhost.isLoopbackAddress() + "");
			
			InetAddress oreilly = InetAddress.getByName("oreilly.com");
			System.out.println(oreilly);
			System.out.println(oreilly.isReachable(500));
			System.out.println(oreilly.isReachable(5000));
			
			InetAddress ibiblio = InetAddress.getByName("www.ibiblio.org");
			InetAddress helios = InetAddress.getByName("helios.metalab.unc.edu");
			if(ibiblio.equals(helios)) {
				System.out.println("www.ibiblio.org is the same as helios.metalab.unc.edu");
			} else {
				System.out.println("www.ibiblio.org is not the same as helios.metalab.unc.edu");
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void p(String msg) {
		System.out.println(msg);
	}

}
