package me.longerian.abc.udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UdpEchoClient {

	public final static int DEFAULT_PORT = 7;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String hostname = "localhost";
		int port = DEFAULT_PORT;
		try {
			InetAddress ia = InetAddress.getByName(hostname);
			Thread sender = new SenderThread(ia, DEFAULT_PORT);
			sender.start();
			Thread receiver = new ReceiverThread(
					((SenderThread) sender).getSocket());
			receiver.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public static class SenderThread extends Thread {

		private InetAddress server;
		private DatagramSocket socket;
		private boolean stopped = false;
		private int port;

		public SenderThread(InetAddress address, int port)
				throws SocketException {
			this.server = address;
			this.port = port;
			this.socket = new DatagramSocket();
			this.socket.connect(server, port);
		}

		public void halt() {
			this.stopped = true;
		}

		public DatagramSocket getSocket() {
			return this.socket;
		}

		@Override
		public void run() {
			try {
				BufferedReader userInput = new BufferedReader(
						new InputStreamReader(System.in));
				while (true) {
					if (stopped)
						return;
					String theLine = userInput.readLine();
					if (theLine.equals("."))
						break;
					byte[] data = theLine.getBytes();
					DatagramPacket output = new DatagramPacket(data,
							data.length, server, port);
					socket.send(output);
					Thread.yield();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	public static class ReceiverThread extends Thread {

		private DatagramSocket socket;
		private boolean stopped = false;

		public ReceiverThread(DatagramSocket ds) {
			this.socket = ds;
		}

		public void halt() {
			this.stopped = true;
		}

		@Override
		public void run() {
			byte[] buffer = new byte[65507];
			while (true) {
				if (stopped)
					return;
				DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
				try {
					socket.receive(dp);
					String s = new String(dp.getData(), 0, dp.getLength());
					System.out.println(s);
					Thread.yield();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}

	}

}
