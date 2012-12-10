package me.longerian.server.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class AESUtil {

	/**
	 * not compatible with CryptoJS
	 * @param content
	 * @param password
	 * @return
	 */
	public static byte[] encrypt(String content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密  not compatible with CryptoJS
	 * 
	 * @param content
	 *            待解密内容
	 * @param password
	 *            解密密钥
	 * @return
	 */
	public static byte[] decrypt(byte[] content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(content);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	    public static void main(String args[]) throws Exception {
	    	 String data = "Test String";
	         String key = "1234567812345678";
	         String iv = "1234567812345678";
	         String encrypted = encrypt(data, key, iv);
	        System.out.println(encrypted);
	        System.out.println(decrypt(encrypted, key, iv));
	    }

	    public static String encrypt(String data, String key, String iv) throws Exception {
	        try {
	            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
	            int blockSize = cipher.getBlockSize();

	            byte[] dataBytes = data.getBytes();
	            int plaintextLength = dataBytes.length;
	            if (plaintextLength % blockSize != 0) {
	                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
	            }

	            byte[] plaintext = new byte[plaintextLength];
	            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
	            
	            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
	            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

	            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
	            byte[] encrypted = cipher.doFinal(plaintext);

	            return Crypto.Utils.byteArrayToBase64String(encrypted);

	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	    public static String decrypt(String data, String key, String iv) throws Exception {
	        try
	        {
	            byte[] encrypted1 = Crypto.Utils.base64StringToByteArray(data);
	            
	            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
	            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
	            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
	            
	            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
	 
	            byte[] original = cipher.doFinal(encrypted1);
	            String originalString = new String(original);
	            return originalString;
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }


}
