package me.longerian.server;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Enumeration;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import me.longerian.server.utils.AESUtil;
import me.longerian.server.utils.Crypto;
import me.longerian.server.utils.DESUtil;
import me.longerian.server.utils.RSAUtil;
import me.longerian.server.utils.Crypto.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class TestServer extends NanoHTTPD {

	public TestServer() throws IOException {
		super(8080, new File("./assert/"));
	}

	public Response serve(String uri, String method, Properties header,
			Properties parms, Properties files) {
		if (uri.startsWith("/crypto")) {
			boolean isget = false;
			String received = null;
			Enumeration e = parms.propertyNames();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = parms.getProperty(key);
				if (key.equals("get") && value.equals("true")) {
					isget = true;
					break;
				}
				received = value;
			}
			if (isget) {
				try {
					Crypto c = new Crypto();
					Crypto.AES aes;
					aes = c.new AES();
					int keySizeInBits = 128;
					int keySizeInBytes = keySizeInBits / 8;

					// Derive a cryptographic key: PBKDF2
					String salt = Utils.byteArrayToHexString(Utils
							.getRandomBytes(8));
					String key = Utils.pbkdf2("Secret Passphrase", salt, 1000,
							keySizeInBytes);

					// generate IV
					byte[] ivBytes = aes.generateIV();
					aes.setIV(ivBytes);
					// String key = "Secret Passphrase";
					/*** encrypt */
					String ciphertext = aes.encrypt("it is a very secret text",
							key);
					myOut.println(ciphertext);
					/*** decrypt */
					String plaintext = aes.decrypt(ciphertext, key);
					myOut.println(plaintext);
					JSONObject json = new JSONObject();
					try {
						json.put("ct", ciphertext);
						json.put("iv", Crypto.parseByte2HexStr(ivBytes));
						json.put("s", salt);
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
					
					return new NanoHTTPD.Response(HTTP_OK, MIME_HTML,
							json.toString());
				} catch (InvalidKeyException e1) {
					e1.printStackTrace();
					return new NanoHTTPD.Response(HTTP_INTERNALERROR,
							MIME_PLAINTEXT, "InvalidKeyException: "
									+ e1.getMessage());
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
					return new NanoHTTPD.Response(HTTP_INTERNALERROR,
							MIME_PLAINTEXT, "NoSuchAlgorithmException: "
									+ e1.getMessage());
				} catch (NoSuchPaddingException e1) {
					e1.printStackTrace();
					return new NanoHTTPD.Response(HTTP_INTERNALERROR,
							MIME_PLAINTEXT, "NoSuchPaddingException: "
									+ e1.getMessage());
				} catch (NoSuchProviderException e1) {
					e1.printStackTrace();
					return new NanoHTTPD.Response(HTTP_INTERNALERROR,
							MIME_PLAINTEXT, "NoSuchProviderException: "
									+ e1.getMessage());
				} catch (InvalidParameterSpecException e1) {
					e1.printStackTrace();
					return new NanoHTTPD.Response(HTTP_INTERNALERROR,
							MIME_PLAINTEXT, "InvalidParameterSpecException: "
									+ e1.getMessage());
				} catch (InvalidAlgorithmParameterException e1) {
					e1.printStackTrace();
					return new NanoHTTPD.Response(HTTP_INTERNALERROR,
							MIME_PLAINTEXT,
							"InvalidAlgorithmParameterException: "
									+ e1.getMessage());
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
					return new NanoHTTPD.Response(HTTP_INTERNALERROR,
							MIME_PLAINTEXT, "UnsupportedEncodingException: "
									+ e1.getMessage());
				} catch (IllegalBlockSizeException e1) {
					e1.printStackTrace();
					return new NanoHTTPD.Response(HTTP_INTERNALERROR,
							MIME_PLAINTEXT, "IllegalBlockSizeException: "
									+ e1.getMessage());
				} catch (BadPaddingException e1) {
					e1.printStackTrace();
					return new NanoHTTPD.Response(HTTP_INTERNALERROR,
							MIME_PLAINTEXT, "BadPaddingException: "
									+ e1.getMessage());
				}
			} else {
				return new NanoHTTPD.Response(HTTP_OK, MIME_HTML, received);
			}
		} else if (uri.startsWith("/aes")) {
			boolean isget = false;
			String received = null;
			Enumeration e = parms.propertyNames();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = parms.getProperty(key);
				if (key.equals("get") && value.equals("true")) {
					isget = true;
					break;
				}
				received = value;
			}
			String key = "1234567812345678";
			String iv = "1234567812345678";
			if (isget) {
				String data = "洪小龙";
				String encrypted = "";
				try {
					encrypted = AESUtil.encrypt(data, key, iv);
				} catch (Exception e1) {
					e1.printStackTrace();
					return new NanoHTTPD.Response(HTTP_INTERNALERROR,
							MIME_PLAINTEXT, "Exception: "
									+ e1.getMessage());
				}
				return new NanoHTTPD.Response(HTTP_OK, MIME_HTML, encrypted);
			} else {
				String decrypted = "";
				try {
					decrypted = AESUtil.decrypt(received.trim().replace(" ", "+"), key, iv);
				} catch (Exception e1) {
					e1.printStackTrace();
					return new NanoHTTPD.Response(HTTP_INTERNALERROR,
							MIME_PLAINTEXT, "Exception: "
									+ e1.getMessage());
				}
				return new NanoHTTPD.Response(HTTP_OK, MIME_HTML, decrypted);
			}
		} else if (uri.startsWith("/md5")) {
			String secretmsg = "Longerian";
			boolean isget = false;
			String received = null;
			Enumeration e = parms.propertyNames();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = parms.getProperty(key);
				if (key.equals("get") && value.equals("true")) {
					isget = true;
					break;
				}
				received = value;
			}
			if (isget) {
				try {
					Crypto c = new Crypto();
					Crypto.HASH hash = c.new HASH();
					return new NanoHTTPD.Response(HTTP_OK, MIME_HTML,
							hash.MD5(secretmsg));
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
					return new NanoHTTPD.Response(HTTP_INTERNALERROR,
							MIME_PLAINTEXT, "NoSuchAlgorithmException: "
									+ e1.getMessage());
				}
			} else {
				return new NanoHTTPD.Response(HTTP_OK, MIME_HTML, received);
			}
		} else if (uri.startsWith("/sha1")) {
			String secretmsg = "Longerian";
			boolean isget = false;
			String received = null;
			Enumeration e = parms.propertyNames();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = parms.getProperty(key);
				if (key.equals("get") && value.equals("true")) {
					isget = true;
					break;
				}
				received = value;
			}
			if (isget) {
				try {
					Crypto c = new Crypto();
					Crypto.HASH hash = c.new HASH();
					return new NanoHTTPD.Response(HTTP_OK, MIME_HTML,
							hash.SHA1(secretmsg));
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
					return new NanoHTTPD.Response(HTTP_INTERNALERROR,
							MIME_PLAINTEXT, "NoSuchAlgorithmException: "
									+ e1.getMessage());
				}
			} else {
				return new NanoHTTPD.Response(HTTP_OK, MIME_HTML, received);
			}
		} else if (uri.startsWith("/sha256")) {
			String secretmsg = "Longerian";
			boolean isget = false;
			String received = null;
			Enumeration e = parms.propertyNames();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = parms.getProperty(key);
				if (key.equals("get") && value.equals("true")) {
					isget = true;
					break;
				}
				received = value;
			}
			if (isget) {
				try {
					Crypto c = new Crypto();
					Crypto.HASH hash = c.new HASH();
					return new NanoHTTPD.Response(HTTP_OK, MIME_HTML,
							hash.SHA256(secretmsg));
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
					return new NanoHTTPD.Response(HTTP_INTERNALERROR,
							MIME_PLAINTEXT, "NoSuchAlgorithmException: "
									+ e1.getMessage());
				}
			} else {
				return new NanoHTTPD.Response(HTTP_OK, MIME_HTML, received);
			}
		} else if (uri.startsWith("/rsa")) {
			String secretmsg = "Longerian";
			boolean isget = false;
			String received = null;
			Enumeration e = parms.propertyNames();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = parms.getProperty(key);
				if (key.equals("get") && value.equals("true")) {
					isget = true;
					break;
				}
				received = value.trim();
			}
			if (isget) {
				try {
					Crypto c = new Crypto();
					Crypto.HASH hash = c.new HASH();
					return new NanoHTTPD.Response(HTTP_OK, MIME_HTML,
							hash.SHA256(secretmsg));
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
					return new NanoHTTPD.Response(HTTP_INTERNALERROR,
							MIME_PLAINTEXT, "NoSuchAlgorithmException: "
									+ e1.getMessage());
				}
			} else {
				try {
					KeyPair kp = RSAUtil.getKeyPair();
					if (kp == null) {
						kp = RSAUtil.generateKeyPair();
					}
					byte[] plain = RSAUtil.decrypt(kp.getPrivate(),
							received.getBytes());
					StringBuilder sb = new StringBuilder();
					sb.append(new String(plain));
					return new NanoHTTPD.Response(HTTP_OK, MIME_HTML,
							sb.toString());
				} catch (Exception e1) {
					e1.printStackTrace();
					return new NanoHTTPD.Response(HTTP_INTERNALERROR,
							MIME_PLAINTEXT, "Exception " + e1.getMessage());
				}
			}
		} else if (uri.startsWith("/base64")) {
			String secretmsg = "Longerian";
			boolean isget = false;
			String received = null;
			Enumeration e = parms.propertyNames();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = parms.getProperty(key);
				if (key.equals("get") && value.equals("true")) {
					isget = true;
					break;
				}
				received = value;
			}
			if (isget) {
				return new NanoHTTPD.Response(HTTP_OK, MIME_HTML,
						Crypto.Utils.base64_encode(secretmsg));
			} else {
				return new NanoHTTPD.Response(HTTP_OK, MIME_HTML, Crypto.Utils.base64_decode(received));
			}
		} else if(uri.startsWith("/des")) {
			boolean isget = false;
			String received = null;
			Enumeration e = parms.propertyNames();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = parms.getProperty(key);
				if (key.equals("get") && value.equals("true")) {
					isget = true;
					break;
				}
				received = value;
			}
			String key = "1234567812345678";
			String iv = "12345678";
			if (isget) {
				 String data = "Test String";
				try {
					String encrypted = DESUtil.encrypt(data, key, iv);
					return new NanoHTTPD.Response(HTTP_OK, MIME_HTML, encrypted);
				} catch (Exception e1) {
					e1.printStackTrace();
					return new NanoHTTPD.Response(HTTP_INTERNALERROR,
							MIME_PLAINTEXT, "Exception " + e1.getMessage());
				}
			} else {
				String decrypted = "";
				try {
					decrypted = DESUtil.decrypt(received.trim().replace(" ", "+"), key, iv);
				} catch (Exception e1) {
					e1.printStackTrace();
					return new NanoHTTPD.Response(HTTP_INTERNALERROR,
							MIME_PLAINTEXT, "Exception: "
									+ e1.getMessage());
				}
				return new NanoHTTPD.Response(HTTP_OK, MIME_HTML, decrypted);
			}
		} else {
			return super.serve(uri, method, header, parms, files);
		}
	}

	public static void main(String[] args) {
		try {
			new TestServer();
		} catch (IOException ioe) {
			System.err.println("Couldn't start server:\n" + ioe);
			System.exit(-1);
		}
		System.out.println("Listening on port 8080. Hit Enter to stop.\n");
		try {
			System.in.read();
		} catch (Throwable t) {
		}
		;
	}
}
