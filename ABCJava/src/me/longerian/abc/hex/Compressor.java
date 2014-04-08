package me.longerian.abc.hex;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


public class Compressor {

	public static byte[] compress(byte[] content) 
	{
		byte[] bytes =  null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gzipOutputStream = null;
		try {
			gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
			gzipOutputStream.write(content);
			gzipOutputStream.flush();
			gzipOutputStream.finish();
			
			bytes =  byteArrayOutputStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(gzipOutputStream != null) {
					gzipOutputStream.close();
				}
				byteArrayOutputStream.close();
			} catch (IOException e) {
			}
		}
        return bytes;
    }

    public static byte[] decompress(byte[] contentBytes) 
    {
    	byte[] bytes =  null;
    	ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(contentBytes);
    	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    	byte[] buffer = new byte[1024];
        GZIPInputStream gzipInputStream = null;
		try {
			gzipInputStream = new GZIPInputStream(byteArrayInputStream);
			int n = 0;
			while((n = gzipInputStream.read(buffer)) != -1) {
				byteArrayOutputStream.write(buffer, 0, n);
			}
			byteArrayOutputStream.flush();
			bytes =  byteArrayOutputStream.toByteArray();; 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(gzipInputStream != null) {
					gzipInputStream.close();
				}
				byteArrayInputStream.close();
				byteArrayOutputStream.close();
			} catch (IOException e) {
			}
		}
        return bytes;
    }
    
}
