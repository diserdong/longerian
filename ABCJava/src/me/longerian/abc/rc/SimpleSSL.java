package me.longerian.abc.rc;

/**
 * @author Kenny
 * rc算法加密、解密文件或数据
 */
public class SimpleSSL {
	static {
		System.load("D:/android_workspace/ABCJava/libs/libsimplessl.so");
	};

	/**
	 * 未实现 TODO
	 * @param fileName
	 * @return
	 */
	public static native byte[] rc(String fileName);

	
	/**
	 * 加密或解密字节数组中长度为length的数据，并返回长度为length的处理后数组；
	 * 如果length大于字节数组长度，返回的数组长度与输入数组长度一致；
	 * 如果length小于等于0，返回空数组；如果bytes为null，也返回空数组。
	 * @param bytes 待处理数据
	 * @param length 处理的长度
	 * @return  处理后的字节数组
	 */
	public static native byte[] rc(byte[] bytes, int length);

}
