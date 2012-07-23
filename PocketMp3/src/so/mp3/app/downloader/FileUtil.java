package so.mp3.app.downloader;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {

	/**
	 * 将数据流保存为文件
	 * @param input  要保存的数据流
	 * @param file 保存后的文件
	 * @return true 保存成功，false 保存失败
	 */
	public static boolean saveStreamAsFile(InputStream input, File file) {
		OutputStream os = null;
		byte buffer[] = new byte[1024];
		try {
			File work = file.getParentFile();
			if (!work.exists()) {
				work.mkdirs();
			}
			os = new BufferedOutputStream(new FileOutputStream(file));
			int length = 0;
			while ((length = input.read(buffer)) != -1) {
				os.write(buffer, 0, length);
			}
			os.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
