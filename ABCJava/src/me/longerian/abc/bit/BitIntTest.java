package me.longerian.abc.bit;

public class BitIntTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 byte[] b=new byte[]{0, 0, 1, 1};
		 System.out.println(bytes2int(b));
		 
		 long dwThreadID = Thread.currentThread().getId();
		 long port = 13088;
		 System.out.println("thread id " + dwThreadID);
		 long channelId = ((dwThreadID << 16) & 0x7fff0000) | (port & 0x0000ffff);
		 System.out.println("channelId id " + channelId);
		 
		 long parsedPort = channelId & 0x0000ffff;
		 System.out.println("parsedPort id " + parsedPort);
	}

	public static int bytes2int(byte[] b) {
		int mask = 0xff;
		int temp = 0;
		int res = 0;
		for (int i = 0; i < 4; i++) {
			res <<= 8;
			temp = b[i];
			res |= temp;
		}
		return res;
	}

}
