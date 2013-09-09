package me.longerian.abc.serialize;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializationTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		ApkInfo mm = new ApkInfo("com.tencent.mm", 500, "5.0", 8, 18, 240004468, "MD5MM", new String[] {"ACCESS_WIFI_STATE", "WRITE_EXTERNAL_STORAGE"}, null, null);
		ApkInfo mm = null;
		ApkInfo qq = new ApkInfo("com.tencent.qq", 400, "402", 7, 18, 45674468, "MD5QQ", null, null, null);
		try {
			FileOutputStream fos = new FileOutputStream("mm.obj");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(mm);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		try {
			FileInputStream fis = new FileInputStream("mm.obj");
			ObjectInputStream ois = new ObjectInputStream(fis);
			ApkInfo mm2 = (ApkInfo) ois.readObject();
			ois.close();
			assert(mm2 == null);
			assert(mm2.equals(mm));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
