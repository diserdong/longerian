package me.longerian.abc.fakeclient;

import com.qq.taf.jce.JceStruct;


/**
 * 封装发送给服务器的请求，包括jcestruct数据对象和cmdid
 * @author Kenny
 */
public class SessionRequest {

	/**
	 * 请求id
	 */
	private int cmdId;
	/**
	 * post出去的内容
	 */
	private JceStruct body;
	
	/**
	 * jce body转化成字节数组
	 */
	private byte[] bodyBytes;
	
	/**
	 * @return 对象内容转成字节数组后的长度
	 */
	public int getTotalLength() {
		if(bodyBytes != null) {
			return bodyBytes.length + 4;
		} else {
			return 0;
		}
	}

	public int getCmdId() {
		return cmdId;
	}

	public JceStruct getBody() {
		return body;
	}
	
	/** 获取待发送cmdid的字节数组 */
	public byte[] getCmdIdBytes() {
		return Command.int2bytes(cmdId);
	}

	/** 获取待发送对象的字节数组 */
	public byte[] getBodyBytes() {
		if(bodyBytes != null) {
			return bodyBytes;
		} else {
			return new byte[0];
		}
	}

	public void setCmdId(int cmdId) {
		this.cmdId = cmdId;
	}

	public void setBody(JceStruct body) {
		this.body = body;
		if(body != null) {
			this.bodyBytes = ProtocolPackage.jceStructToUTF8Byte(body);
		} else {
			this.bodyBytes = null;
		}
	}
	
}
