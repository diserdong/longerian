package me.longerian.abc.fakeclient;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * 将服务器返回的http内容解析出来，数据包有可能是HTTP响应，也有可能是HTTP请求，
 * 主要关心：1.响应码（如果是收到请求数据，响应码置为200），2.内容长度，3.内容（cmdid和jcestruct数据对象）
 * @author Kenny
 */
public class SessionResponse {

	private static final String TAG = "SessionResponse";
	
	public static final int COMMAND_VER_RESP_ID = 19982013;
	private static final byte[] VER_RESP = "AccessServer".getBytes();
	/**
	 * http响应码
	 */
	private int responseCode;
	/**
	 * http内容包长度
	 */
	private int contentLength;
	/**
	 * http内容包
	 */
	private ByteBuffer responseBody;
	/**
	 * http内容包中包含的接口id
	 */
	private int cmdId;
	
	private static StringBuilder sb = new StringBuilder();
	
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}

	public void setResponseBody(ByteBuffer responseBody) {
		this.responseBody = responseBody;
	}

	public void setCmdId(int cmdId) {
		this.cmdId = cmdId;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public int getContentLength() {
		return contentLength;
	}

	public ByteBuffer getResponseBody() {
		return responseBody;
	}

	public int getCmdId() {
		return cmdId;
	}
	

	/**
	 * 负责解析http输入流，获取基本信息，创建SessionResponse对象，该方法会一直阻塞到有数据为止。
	 * @param input 输入流
	 * @return 创建的SessionResponse方法
	 * @throws IOException 输入流被关闭或者网络有异常
	 */
	public static SessionResponse parseFromStream(DataInputStream input) throws IOException {
		if(input == null) {
			return null;
		}
		String line = input.readLine();
		if (line == null || line.equals("")) {
			return null;
		}
		SessionResponse response = new SessionResponse();
		response.responseCode = getHttpResponseCode(line);
		while (line != null && !line.equals("")) {
			System.out.println(line);
			sb.append(line).append("\r\n");
			if (line.startsWith("Content-Length")) {
				response.contentLength = getHttpContentLength(line);
			}
			line = input.readLine();
		}
		response.responseBody = getHttpResponseBody(input, response.responseCode, response.contentLength);
		if(response.responseBody != null) {
//			WcsLogger.f(TAG + " " + sb.toString());
//			WcsLogger.f(TAG + " " + new String(response.responseBody.array(), 0 , response.contentLength));
			System.out.println(new String(response.responseBody.array(), 0 , response.contentLength));
			if(Command.ByteStartWith(response.responseBody.array(), VER_RESP)) {
				//访问version接口的返回，特殊处理
				response.cmdId = COMMAND_VER_RESP_ID;
			} else {
				if(response.contentLength > 4 && response.contentLength == response.responseBody.limit()) {
					response.cmdId = Command.bytes2int(response.responseBody.array());
				}
			}
		} else {
			input.skip(response.getContentLength());
		}
		sb.delete(0, sb.length());
		System.out.println("===========");
		return response;
	}
	
	private static int getHttpResponseCode(String line) {
		if (line == null) {
			return -1;
		}
		line = line.trim();
		int index = line.indexOf(' ');
		if (index < 0 || (index + 1) >= line.length()) {
			return -1;
		}
		line = line.substring(index + 1);
		if (line == null) {
			return -1;
		}
		line = line.trim();
		index = line.indexOf(' ');
		if (index < 0 || index >= line.length()) {
			return -1;
		}
		line = line.substring(0, index);
		try {
			return Integer.parseInt(line);
		} catch (Exception e) {
			//认为推送的是http请求，也当作是200
			return 200;
		}
	}
	
	private static int getHttpContentLength(String line) {
		if (line == null) {
			return -1;
		}
		int index = line.indexOf(':');
		if (index < 0 || (index + 1) >= line.length()) {
			return -1;
		}
		line = line.substring(index + 1);
		if (line == null) {
			return -1;
		}
		line = line.trim();
		try {
			return Integer.parseInt(line);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	private static ByteBuffer getHttpResponseBody(DataInputStream dis, int responseCode, int contentLength) throws IOException {
		if (dis == null) {
			return null;
		}
		if (contentLength <= 0) {
			return null;
		}
		if (responseCode != 200) {
			//有可能收到错误500 502 504等消息
		}
		ByteBuffer bytes = ByteBuffer.allocate(48 * 1024);
		int totalRead = 0;
		int read = 0;
		while (totalRead < contentLength) {
			int needRead = contentLength - totalRead;
			if (needRead > 1024 * 8) {
				needRead = 1024 * 8;
			}
			read = dis.read(bytes.array(), totalRead, needRead);
			if (read > 0) {
				totalRead += read;
			} else if (read == -1) {
				break;
			}
		}
		if (totalRead < contentLength) {
			return null;
		}
		bytes.position(4); //偏移4个字节，因为头4个字节是命令码
		bytes.limit(contentLength);
		return bytes;
	}
	
}
