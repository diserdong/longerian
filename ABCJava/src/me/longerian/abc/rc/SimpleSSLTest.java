package me.longerian.abc.rc;

public class SimpleSSLTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("length = " + "a".getBytes().length);
		byte[] b = SimpleSSL.rc("a".getBytes(), 10);
		System.out.println(b.toString() + "===" + b.length);
		byte[] rb = SimpleSSL.rc(b, 10);
		System.out.println(new String(rb));
	}

}
