package me.longerian.abc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostLookup {

	public static void main(String[] args) {
		if(args.length > 0) {
			for(int i = 0; i < args.length; i++) {
				System.out.println(lookup(args[i]));
			}
		} else {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("enter names and ip addresses. enter \"exit\" to quit.");
			try{
				while(true) {
					String host = in.readLine();
					if(host.equalsIgnoreCase("exit") || host.equalsIgnoreCase("quit")) {
						break;
					}
					System.out.println(lookup(host));
				}
			} catch(IOException ex) {
				System.err.println(ex);
			}
		}
		
		
	}

	private static String lookup(String host) {
		InetAddress node;
		try{
			node = InetAddress.getByName(host);
		} catch(UnknownHostException ex) {
			return "can not find host " + host;
		}
		if(isHostname(host)) {
			return node.getHostAddress();
		} else {
			return node.getHostName();
		}
	}

	private static boolean isHostname(String host) {
		if(host.indexOf(":") != -1) return false;
		char[] ca = host.toCharArray();
		for(int i = 0; i < ca.length; i++) {
			if(!Character.isDigit(ca[i])) {
				if(ca[i] != '.') return true;
			}
		}
		return false;
	}
}
