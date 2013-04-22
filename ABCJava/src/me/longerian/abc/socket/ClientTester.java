package me.longerian.abc.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int port = 80;
		try {
			ServerSocket server = new ServerSocket(port, 1);
			p("listening for connections on port " + server.getLocalPort());
			while(true) {
				Socket connection = server.accept();
				try {
					p("connection established with " + connection);
					Thread input = new InputThread(connection.getInputStream());
					input.start();
					Thread output = new OutputThread(connection.getOutputStream());
					output.start();
					try {
						input.join();
						output.join();
					} catch(InterruptedException ex) {
						
					}
				} catch(IOException ex) {
					
				} finally {
					try {
						if(connection != null) connection.close();
					} catch (IOException e) {
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void p(String str) {
		System.out.println(str);
	}
	
	static class InputThread extends Thread {
		
		InputStream in;
		public InputThread(InputStream in) {
			this.in = in;
		}
		
		@Override
		public void run() {
			try {
				while(true) {
					int i = in.read();
					if(i == -1) break;
					System.out.write(i);
				}
			} catch(IOException ex) {
				
			}
			try {
				in.close();
			} catch(IOException ex) {
				
			}
		}
	}
	
	static class OutputThread extends Thread {
		
		private Writer out;
		
		public OutputThread(OutputStream out) {
			this.out = new OutputStreamWriter(out);
		}

		@Override
		public void run() {
			String line;
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			try {
				while(true) {
					line = in.readLine();
					if(line.equals(".")) break;
					out.write(line);
					out.write("\r\n");
					out.flush();
				}
			} catch(IOException ex) {
				
			}
			try {
				out.close();
			} catch(IOException ex) {
				
			}
		}
		
	}

}
