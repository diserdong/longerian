package me.longerian.abc;

public class ParserResponseCode {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String head = "POST / HTTP/1.1";
		int code = getHttpResponseCode(head);
		System.out.println(code);
	}

	private static int getHttpResponseCode(String line) {
		if (line == null) {
			return -1;
		}
		line = line.trim();
		if(line.startsWith("POST")) {
			//认为推送的是http请求，也当作是200
			return 200;
		}
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
	
}
