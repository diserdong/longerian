package me.longerian.abc.hex;

import com.qq.taf.jce.JceInputStream;

public class HexToByteArray {

	private static String hexStr =  "0123456789abcdef";  
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("========echo lost=========");
		printTransDataMessage("0226fc2f3716083431323162333130262039353933663432303065633934363233613766326538623536663537666435653d000002090c4226fc01ff503d600370028c");
		System.out.println("========echo ack=========");
		printTransDataMessage("0226fc2f3716083431323162333130262039353933663432303065633934363233613766326538623536663537666435654226fc01ff503c60047c8c");
		System.out.println("========rcv =========");
		printTransDataMessage("0226fc2f3816203935393366343230306563393436323361376632653862353666353766643565260834313231623331303d000c4226fc02a9500260027c8c");
	}
	
	private static void printTransDataMessage(String hexStream) {
		System.out.println("========start=========");
		byte[] raw = hexString2Binary(hexStream);
		JceInputStream tdmJcs = new JceInputStream(raw);
		TransDataMessage msg = new TransDataMessage();
		msg.readFrom(tdmJcs);
		System.out.println("type = " + msg.eType);
		System.out.println("chnl = " + msg.nChannelId);
		System.out.println("size = " + msg.nDataLen);
		System.out.println("isCompressed = " + msg.nIsCompress);
		System.out.println("msgId = " + msg.nMessageId);
		System.out.println("seqId = " + msg.nSequenceId);
		System.out.println("dstId = " + msg.sDestDeviceId);
		System.out.println("srcId = " + msg.sSrcDeviceId);
		System.out.println("========end=========");
	}
	
	/** 
     *  
     * @param hexString 
     * @return 将十六进制转换为字节数组 
     */  
    public static byte[] hexString2Binary(String hexString){  
        //hexString的长度对2取整，作为bytes的长度  
        int len = hexString.length()/2;  
        byte[] bytes = new byte[len];  
        byte high = 0;//字节高四位  
        byte low = 0;//字节低四位  
  
        for(int i=0;i<len;i++){  
             //右移四位得到高位  
             high = (byte)((hexStr.indexOf(hexString.charAt(2*i)))<<4);  
             low = (byte)hexStr.indexOf(hexString.charAt(2*i+1));  
             bytes[i] = (byte) (high|low);//高地位做或运算  
        }  
        return bytes;  
    }

}
