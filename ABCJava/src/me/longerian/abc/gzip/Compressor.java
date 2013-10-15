package me.longerian.abc.gzip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Compressor {

	public static byte[] compress(byte[] content) throws IOException{
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        gzipOutputStream.write(content);
        gzipOutputStream.finish();
        gzipOutputStream.flush();
        gzipOutputStream.close();
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] decompress(byte[] contentBytes) throws IOException {
    	ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(contentBytes);
        GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int n = 0;
        while((n = gzipInputStream.read(buffer)) != -1) {
        	byteArrayOutputStream.write(buffer, 0, n);
        }
        byteArrayOutputStream.flush();
        gzipInputStream.close();
        byteArrayInputStream.close();
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }
    
}
