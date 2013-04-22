package me.longerian.abc.socket;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Socket;
import java.net.UnknownHostException;

public class DaytimeClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String host = "time.nist.gov";
		try {
			Socket theSocket = new Socket("127.0.0.1", 13);
			InputStream in = theSocket.getInputStream();
			in = new BufferedInputStream(in);
			Reader r = new InputStreamReader(in);
			int c;
			while((c = r.read()) != -1) System.out.write(c);
			in.close();
			theSocket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
