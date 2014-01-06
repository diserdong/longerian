package me.longerian.abc.encrypt;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import me.longerian.abc.encrypt.Base64;

public class Des3 {
	public static void main(String[] args) throws Exception {

//        byte[] key=Base64.decode("YWJjZGVmZ2hpamtsbW5vcHFyc3R1dnd4".getBytes()/*,Base64.DEFAULT*/);
		byte[] key = "TencentMolo&&##%%!!!1234".getBytes();
        byte[] keyiv = { 1, 2, 3, 4, 5, 6, 7, 8 };

        byte[] data="abcde".getBytes("UTF-8");
        
        System.out.println("ECB加密解密");
        byte[] str3 = des3EncodeECB(key,data );
        byte[] str4 = des3DecodeECB(key, str3);
        
        
        System.out.println("encrpyted length " + str3.length);
        System.out.println("decrpyted length " + str4.length);
        
        for (int i = 0; i < str3.length; i++) { 
	    	String hex = Integer.toHexString(str3[i] & 0xFF); 
	    	if (hex.length() == 1) { 
	    		hex = '0' + hex; 
	    	}
	    	System.out.print(hex.toLowerCase());
    	} 
        System.out.println();
        	
        System.out.println(new String(Base64.encode(str3/*,  Base64.DEFAULT*/),"UTF-8"));
        System.out.println(new String(str4, "UTF-8"));


        System.out.println("CBC加密解密");
        byte[] str5 = des3EncodeCBC(key, keyiv, data);
        byte[] str6 = des3DecodeCBC(key, keyiv, str5);
        System.out.println(new String(Base64.encode(str5  /*,  Base64.DEFAULT*/),"UTF-8"));
        System.out.println(new String(str6, "UTF-8"));
    }

	/**
	 * ECB加密,不要IV
	 * 
	 * @param key
	 *            密钥
	 * @param data
	 *            明文
	 * @return 密文
	 * @throws Exception
	 */
	public static byte[] des3EncodeECB(byte[] key, byte[] data)
			throws Exception {

		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);

		Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");

		cipher.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] bOut = cipher.doFinal(data);

		return bOut;
	}

	/**
	 * ECB解密,不要IV
	 * 
	 * @param key
	 *            密钥
	 * @param 密文
	 * @return 明文
	 * @throws Exception
	 */
	public static byte[] des3DecodeECB(byte[] key, byte[] data)
			throws Exception {

		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);

		Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");

		cipher.init(Cipher.DECRYPT_MODE, deskey);

		byte[] bOut = cipher.doFinal(data);

		return bOut;

	}

	/**
	 * CBC加密
	 * 
	 * @param key
	 *            密钥
	 * @param keyiv
	 *            IV
	 * @param data
	 *            明文
	 * @return 密文
	 * @throws Exception
	 */
	public static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data)
			throws Exception {

		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);

		Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(keyiv);
		cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
		byte[] bOut = cipher.doFinal(data);

		return bOut;
	}

	/**
	 * CBC解密
	 * 
	 * @param key
	 *            密钥
	 * @param keyiv
	 *            IV
	 * @param 密文
	 * @return 明文
	 * @throws Exception
	 */
	public static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data)
			throws Exception {

		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);

		Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(keyiv);

		cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

		byte[] bOut = cipher.doFinal(data);

		return bOut;

	}

}
