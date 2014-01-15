package me.longerian.abc.hex;

import com.qq.taf.jce.JceInputStream;

public class HexToByteArray {

	private static String hexStr =  "0123456789abcdef";  
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		System.out.println("========echo lost=========");
//		printTransDataMessage("0226fc2f3716083431323162333130262039353933663432303065633934363233613766326538623536663537666435653d000002090c4226fc01ff503d600370028c");
//		System.out.println("========echo ack=========");
//		printTransDataMessage("0226fc2f3716083431323162333130262039353933663432303065633934363233613766326538623536663537666435654226fc01ff503c60047c8c");
//		System.out.println("========rcv =========");
//		printTransDataMessage("0226fc2f3816203935393366343230306563393436323361376632653862353666353766643565260834313231623331303d000c4226fc02a9500260027c8c");
//		System.out.println("========contact 0=========");
//		printTransDataMessage("0c1608343132316233313026285441465f66363033316666612d316637352d343535372d386631312d6139333165363237313237663d0000109110a22d5b32bd1070e2656bd500d910401a5c6c700f8c");
//		System.out.println("========contact 1 =========");
//		printTransDataMessage("0c1608343132316233313026285441465f66363033316666612d316637352d343535372d386631312d6139333165363237313237663d00000814a69a56ecc240ed401a50016c70028c");
//		System.out.println("========contact 2 =========");
//		printTransDataMessage("0c1608343132316233313026285441465f66363033316666612d316637352d343535372d386631312d6139333165363237313237663d0000703de63f567bc94aa62a489e6ddb1c77425f53aa8b8bf6342c31358b056edf46f72c16b58651ea73a39f0010204d1e56f3ae486d94780d5ecce023258a74c34e1a74f15358a4c62d7d6a69d9206c8a8073e111a331debce6b4fd3cb444b1e0db786d612d869c6323a3b0fd1206c6b37191401a50026c706c8c");
//		System.out.println("========contact 3 =========");
//		printTransDataMessage("0c1608343132316233313026285441465f66363033316666612d316637352d343535372d386631312d6139333165363237313237663d00010168635225bc962dc464c28330a8a65358dbff181ff0ca9cf031709b659e3f13deb99969320606bae2548982afdf0e959e1e035d3107f755fdc397749e4e2a984fa14e4b1c5fa2b10f75e91c638047fb3868fabb21fa51f9a1fb6ec78f99b33113429c12c91a7b03c3880aca6a73593cdd2c1c28559e35bee6b32d1a0d5f5dc9f1c89da7214fd79fefef5fda62a21c5644d6f18c507edef6f5a5f3c4d6c831bf1df4b01bfd30559b09093de1b05359971900cb8af544f0d27f1fe08022abcf3145a87d8060a3b92d908840b527c0aa5cbbe8dc6e40ebb0e63ab655661dc49564bae0ab24213578e7963db6cbcd9abe7e760f5a05c58c8e9aab953d469c9fb8dfb8f011035a5f3cc8ed781940a328858bba1c65d2ee7eca1badf37a5c34f248055b65fe8ebc76c4beb2eedcd312ff6f0abe6f732accaa0a5fb8b0219c5b83f8ad318b0cbbbf7c5a71ea9ef2c1c4c3412ae0c35e0484f28fbdfb5eba2a04316866dbbb10c7c962cba6dab9401a50036c7101668001");
//		System.out.println("========contact 4 =========");
//		printTransDataMessage("0c1608343132316233313026285441465f66363033316666612d316637352d343535372d386631312d613933316536323731323766401a500460027c8c");
		
		printTransDataMessage("0c1608343132316233313026285441465f32303865336165322d333665372d343231662d396161362d3565346233653466646665353d0000109110a22d5b32bd1070e2656bd500d910400a5c6c700f8c");
		printTransDataMessage("0c1608343132316233313026285441465f32303865336165322d333665372d343231662d396161362d3565346233653466646665353d00000814a69a56ecc240ed400a50016c70028c");
		printTransDataMessage("0c1608343132316233313026285441465f32303865336165322d333665372d343231662d396161362d356534623365346664666535400a500360027c8c");
		printTransDataMessage("0c1608343132316233313026285441465f32303865336165322d333665372d343231662d396161362d3565346233653466646665353d000101b8635225bc962dc464954198cc79255474f723d091cdc9d059ea95039febd8b1f0e40c69486ca72fe83ad81804f75882ea34332c684060f89bc082d3ade192eacef280abbde771eeeff859119813ed742523382728943bc458dd7d412e1a5ed701ec97e3d5d986094e07de5a461ad507b01a0db200231a32e1145bff9b9c659977783076b4a5c876affb6a0fb12faf30664c0a04db54d0d7e78525414a90ba7a88031c687c6d11107b018ffd693530b22cbbb0f68a0c886332b5a6d72c4e2ab6dfdb6521d54023e35cee769c967fbbb2364d73ad543aac3de33bc7d7f47180799cc6564fa6e6967fea48072444c033c09e91dfd4de88beb3d1f94404411f7557f183620ec46047b2de7222950b213d6f45c78f636e91e8e20704d05d5a4d6af4fa947c6d8b5b013a55fc2f44cf0edea5d214e8fcd741546f7412da3e69c3fc26e2f5c817d876197f721a2b718bd0596ee1a096a3da0d6d777ee2a9ab02335e4bd0e16bb4bba72ac139ea2327f5d1d235615d45fd9fa8039138fbd70ddcb633a4838428aad653e2a036a138dca8ef1b3fc6cf30ac5ed1b270c3a8fb53d89473900c59d95e2dbe9dc45775a7bd434cac2b816718160adb3780cc400a50026c7101b18001");
		
		
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
		
		if(msg.eType == 0 && msg.getVecMessage() != null && msg.getVecMessage().length > 0) {
			byte[] decrypted;
			try {
				decrypted = Des3.des3DecodeECB(PRIVATE_KEY.getBytes("UTF-8"), msg.getVecMessage());
				msg.setVecMessage(decrypted);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(msg.eType == 0 && msg.getVecMessage() != null && msg.getVecMessage().length > 0 && msg.getNIsCompress() == 1) {
			//nIsCompress == 1表明数据已压缩，那就解压缩
			byte[] decompressed = Compressor.decompress(msg.getVecMessage());
			msg.setVecMessage(decompressed);
		}
		
		if(msg.eType == 0 && msg.getVecMessage() != null) {
			System.out.println("content = " + new String(msg.getVecMessage()));
		}
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

    public static final String PRIVATE_KEY = "TencentMolo&&##%%!!!1234";
    
}
