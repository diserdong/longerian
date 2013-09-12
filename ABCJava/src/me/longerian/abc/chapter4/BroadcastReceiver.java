package me.longerian.abc.chapter4;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class BroadcastReceiver {

	public static void main(String args[])throws Exception{  
        receiveBroadcast();  
    }  
      
    public static void receiveBroadcast()throws Exception{  
        byte[] buffer = new byte[65507];  
        DatagramSocket server = new DatagramSocket(8300);  
        DatagramPacket packet = new DatagramPacket(buffer , buffer.length);  
        for(;;){  
            server.receive(packet);  
            String s = new String(packet.getData( ), 0, packet.getLength( ));  
            System.out.println(packet.getAddress( ) + " at port "   
                       + packet.getPort( ) + " says " + s);  
        }  
    }  
    
}
