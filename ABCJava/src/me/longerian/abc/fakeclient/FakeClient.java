package me.longerian.abc.fakeclient;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;

import com.qq.taf.jce.JceInputStream;

public class FakeClient {

	private static final String UTF8 = "UTF-8";
	private static final byte[] CRLF = new byte[] { 13, 10 };
	private static final int SO_TIMEOUT = 30 * 1000;
	private static final int CONNECT_TIMEOUT = 30 * 1000;

	private static DataInputStream dataInput;
	private static InputStream input;
	private static OutputStream output;

	private static URL url;
	
	public static void main(String[] args) throws IOException {
		try {
			url = new URL("http://112.90.140.215:10001/");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			return;
		}
		InetSocketAddress address = new InetSocketAddress(url.getHost(),
				url.getPort());
		Socket client = new Socket();
		try {
			client.setTcpNoDelay(true);
			client.setKeepAlive(false);
			client.setSoTimeout(SO_TIMEOUT);
			client.connect(address, CONNECT_TIMEOUT);
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			return;
		} catch (SocketException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		if (client != null) {
			try {
				input = client.getInputStream();
				dataInput = new DataInputStream(input);
				output = client.getOutputStream();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		while(true) {
			SessionRequest body = getHeartbeat();
			byte[] requestLine = buildRequestLine(url.getHost(), null);
			byte[] hostLine = buildHostLine(url.getHost(), url.getPort());
			byte[] contentLine = buildContentLengthLine(body.getTotalLength());
			output.write(requestLine);
			output.write(CRLF);
			output.write(hostLine);
			output.write(CRLF);
			output.write(contentLine);
			output.write(CRLF);
			output.write(CRLF);
			output.flush();
			output.write(body.getCmdIdBytes());
			output.write(body.getBodyBytes());
			output.flush();
			SessionResponse resp = SessionResponse.parseFromStream(dataInput);
			
			body = getRequest();
			requestLine = buildRequestLine(url.getHost(), null);
			hostLine = buildHostLine(url.getHost(), url.getPort());
			contentLine = buildContentLengthLine(body.getTotalLength());
			output.write(requestLine);
			output.write(CRLF);
			output.write(hostLine);
			output.write(CRLF);
			output.write(contentLine);
			output.write(CRLF);
			output.write(CRLF);
			output.flush();
			output.write(body.getCmdIdBytes());
			output.write(body.getBodyBytes());
			output.flush();
			resp = SessionResponse.parseFromStream(dataInput);
			resp = SessionResponse.parseFromStream(dataInput);
			JceInputStream tdmJcs = new JceInputStream(resp.getResponseBody());
			TransDataMessage responseTransDataMsg = new TransDataMessage();
			responseTransDataMsg.readFrom(tdmJcs);
			if(responseTransDataMsg.getVecMessage() != null && responseTransDataMsg.getVecMessage().length > 0) {
				try {
					responseTransDataMsg.setVecMessage(Des3.des3DecodeECB(WcsConfig.PRIVATE_KEY.getBytes("UTF-8"), responseTransDataMsg.getVecMessage()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(responseTransDataMsg.getVecMessage() != null && responseTransDataMsg.getVecMessage().length > 0 && responseTransDataMsg.getNIsCompress() == 1) {
				//nIsCompress == 1表明数据已压缩，那就解压缩
				byte[] decompressed = Compressor.decompress(responseTransDataMsg.getVecMessage());
				responseTransDataMsg.setVecMessage(decompressed);
			}
			
			System.out.println("vec msg start");
			System.out.println(new String(responseTransDataMsg.vecMessage));
			System.out.println("vec msg end");
			
			try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static SessionRequest getHeartbeat() {
		MHeartBeat requestHeartBeat = new MHeartBeat(0, 0, "fakeclient");
		SessionRequest body = new SessionRequest();
		body.setCmdId(100);
		body.setBody(requestHeartBeat);
		return body;
	}
	
	public static SessionRequest getRequest() {
		TransDataMessage msg = new TransDataMessage();
		msg.nChannelId = 0;
		msg.nMessageId = 100001;
		msg.sSrcDeviceId = "fakeclient";
		msg.sDestDeviceId = "13739753674452bbccac0";
		msg.nSequenceId = 0;
		msg.eType = TransMessageType._TRANS_MESSAGE_TYPE_DATA;
		
		if(requestData.length > 256) {
			msg.vecMessage = Compressor.compress(requestData);
			msg.nIsCompress = 1;
		} else {
			msg.vecMessage = requestData;
			msg.nIsCompress = 0;
		}
		msg.nDataLen = msg.vecMessage.length;
		try {
			msg.vecMessage = Des3.des3EncodeECB(WcsConfig.PRIVATE_KEY.getBytes("UTF-8"), msg.vecMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		SessionRequest body = new SessionRequest();
		body.setCmdId(1801);
		body.setBody(msg);
		return body;
	}
	
	private static byte[] requestData = "GET /sys?act=login&pwd=3494 HTTP/1.1\r\nUser-Agent: Fiddler\r\nHost: 10.96.90.15:8899\r\n\r\n".getBytes();

	private static byte[] buildRequestLine(String host, String path) {
		byte[] line = null;
		if(path == null || path.length() == 0) {
			path = "";
		}
		try {
			line = ("POST http://" + host + "/" + path + " HTTP/1.1").getBytes(UTF8);
		} catch (UnsupportedEncodingException e) {
			line = ("POST http://" + host + "/" + path + " HTTP/1.1").getBytes();
		}
		return line;
	}
	
	private static byte[] buildHostLine(String host, int port) {
		byte[] line = null;
		try {
			line = ("Host: " + host + ":" + port).getBytes(UTF8);
		} catch (UnsupportedEncodingException e) {
			line = ("Host: " + host + ":" + port).getBytes();
		}
		return line;
	}
	
	private static byte[] buildContentLengthLine(int length) {
		byte[] line = null;
		try {
			line = ("Content-Length: " + length).getBytes(UTF8);
		} catch (UnsupportedEncodingException e) {
			line = ("Content-Length: " + length).getBytes();
		}
		return line;
	}
	
}
