package me.longerian.abc.hex;

import java.net.URLDecoder;

public class PcIdDecoder {

	private final static String QRCODE_KEY = "9O58/AGBy2Zx41%3A53^3D4B";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		String userId = "Fjtm5qER80cNLa04mQpfCyfYsq6Hp+cTrbzJBQEeft+vhzuq6aXLt5jt3q3gb5XrlZpuxp/kePkNZewsO2tjdPUFcMpg24wn";
		String userId = "LjeWFsqUYeiq/z7l99gVof1EWaFCuwahPB9/AiDQrfoc0L8KskWjrlCUEZZJUrKUJEsN0RbVEHBjgGYpuMNgpA==";
		String pcId = decodePcId(userId);
		System.out.println(pcId);
	}

	public static String decodePcIdFromQrcode(String qrcode) {
		String result = null;
		if(qrcode == null) {
			return result;
		}
		String userId = decodeUserIdFromQrcode(qrcode);
		if(userId != null) {
			result = decodePcId(userId);
		}
		return result;
	}
	
	public static String decodePcId(String rawPcId) {
		String result = null;
		if(rawPcId == null) {
			return null;
		}
		try {
			byte[] pcidByte = Des3.des3DecodeECB(QRCODE_KEY.getBytes("UTF-8"), Base64.decode(rawPcId.getBytes("UTF-8")));
			result = new String(pcidByte);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(result != null) {
			int empty = result.indexOf(" ");
			if(empty > 0) {
				result = result.substring(empty + 1);
			}
		} else {
			result = rawPcId;
		}
		return result;
	}

	public static String decodeUserIdFromQrcode(String qrcode) {
		String result = null;
		if(qrcode == null) {
			return result;
		}
		String rawPcId = null;
		try {
			byte[] pcidByte = Des3.des3DecodeECB(QRCODE_KEY.getBytes("UTF-8"), Base64.decode(URLDecoder.decode(qrcode, "UTF-8")));
			rawPcId = new String(pcidByte);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(rawPcId != null && rawPcId.startsWith("pcid=")) {
			int pcIdIndex = rawPcId.indexOf("pcid=");
			int firstAndIndexAfterPcId =  rawPcId.indexOf('&', pcIdIndex);
			if(firstAndIndexAfterPcId > 0) {
				result = rawPcId.substring(pcIdIndex + 5, firstAndIndexAfterPcId);
			}
		}
		return result;
	}
	
}
