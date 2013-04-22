package me.longerian.abc;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ContentGetter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			URL u = new URL("http://files.leiphone.com/uploads/01-0/-3/01-08-37-19.jpg");
			Object o = u.getContent();
			System.out.println("I got a " + o.getClass().getName());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
