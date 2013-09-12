package me.longerian.abc.chapter4;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class BroadcastSender {

	public static void main(String args[])throws Exception{  
        sendBroadcast();  
    }  
      
    public static void sendBroadcast()throws Exception{  
        DatagramSocket socket;  
        DatagramPacket packet;  
        byte[] data={1,2,3,4};  
          
        socket = new DatagramSocket();  
        socket.setBroadcast(true); //有没有没啥不同  
        //send端指定接受端的端口，自己的端口是随机的  
        packet = new DatagramPacket(data,data.length,InetAddress.getByName("255.255.255.255"),8300);  
        for(int i = 0 ; i < 50 ; i++){  
            Thread.sleep(1000);  
            socket.send(packet);  
        }  
    }  
      
    
}
