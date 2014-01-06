package me.longerian.abc.fakeclient;

import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;

/**
 * 负责将xxxRequest 打包到 Request。将byte[] 解包成 xxxResponse
 * 
 * @author jaren
 * 
 */
public class ProtocolPackage {

	public static final String ServerEncoding = "utf-8";

	// 将JCE结构转换成UTF8编码的 字节流
	public static byte[] jceStructToUTF8Byte(JceStruct struct) {
		if (struct == null) {
			return null;
		}

		JceOutputStream outputStream = new JceOutputStream();
		outputStream.setServerEncoding(ServerEncoding);
		struct.writeTo(outputStream);
		return outputStream.toByteArray();
	}

}
