package me.longerian.abc.socket;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Redirector implements Runnable {
	
	private int port;
	private static String newSite;

	public Redirector(String site, int port) {
		this.port = port;
		this.newSite = site;
	}
	
	@Override
	public void run() {
		try {
			ServerSocket server = new ServerSocket(this.port);
			System.out.println("redirecting connections on port " + server.getLocalPort() + " to " + newSite);
			while(true) {
				try {
					Socket s = server.accept();
					Thread t = new RedirectThread(s);
					t.start();
				} catch(IOException ex) {
					
				}
			}
			
		} catch(BindException ex) {
			
		} catch(IOException ex) {
			
		}
	}
	
	static class RedirectThread extends Thread {
		
		private Socket connection;
		
		public RedirectThread(Socket socket) {
			this.connection = socket;
		}

		@Override
		public void run() {
			try {
				Writer out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "ASCII"));
				Reader in = new InputStreamReader(new BufferedInputStream(connection.getInputStream()));
				StringBuffer request = new StringBuffer(80);
				while(true) {
					int c = in.read();
					if(c == '\r' || c == '\n' || c == -1) break;
					request.append((char)c);
				}
				String get = request.toString();
				int firstSpace = get.indexOf(' ');
				int secondSpace = get.indexOf(' ', firstSpace + 1);
				String theFile = get.substring(firstSpace + 1, secondSpace);
				if(get.indexOf("HTTP/") != -1) {
					out.write("HTTP/1.0 302 FOUND\r\n");
					Date now = new Date();
					out.write("Date: " + now + "\r\n");
					out.write("Server: Redirector 1.0 \r\n");
					out.write("Location: " + newSite + theFile + "\r\n");
					out.write("Content-Type: text/html\r\n");
					out.write("\r\n");
					out.flush();
				}
				out.write("the document has been moved");
				out.flush();
			} catch(IOException ex) {
				
			} finally {
				try {
					if(connection != null) connection.close();
				} catch(IOException ex) {
					
				}
			}
		}
		
		
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int thePort = 80;
		String theSite = "http://www.baidu.com";
		
		Thread t = new Thread(new Redirector(theSite, thePort));
		t.start();
		
	}

}
