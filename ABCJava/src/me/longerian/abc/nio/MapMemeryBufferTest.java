package me.longerian.abc.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

public class MapMemeryBufferTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		RandomAccessFile raFile = new RandomAccessFile("d:\\crash.log", "rw"); 
		FileChannel fileChannel = raFile.getChannel();
		System.out.println("file channel size: " + fileChannel.size());
		MappedByteBuffer rw = fileChannel.map(MapMode.READ_WRITE, 0, 39);
		for(int i = 0; i < rw.limit(); i++) {
			System.out.print((char) rw.get(i));
		}
		rw.put("--|--".getBytes());
		
		for(int i = 0; i < rw.limit(); i++) {
			System.out.print((char) rw.get(i));
		}
		fileChannel.close();
		raFile.close();

		long start = System.currentTimeMillis();
		nioCopy("d:\\FlashLight.apk", "d:\\FlashLight-copy.apk");
		long end = System.currentTimeMillis();
		System.out.println("nio copy cost: " + (end - start) + "ms");
		start = System.currentTimeMillis();
		commonCopy("d:\\FlashLight-copy.apk", "d:\\FlashLight-copy-copy.apk");
		end = System.currentTimeMillis();
		System.out.println("common copy cost: " + (end - start) + "ms");
//		nioCopy("d:\\crash.log", "d:\\crash-copy.log");
	}
	
	private static void commonCopy(String src, String dst) throws IOException {
		FileInputStream srcFile = new FileInputStream(src);
		FileOutputStream dstFile = new FileOutputStream(dst);
		byte[] buffer = new byte[8192];
		int readed = 0;
		while((readed = srcFile.read(buffer, 0, buffer.length)) != -1) {
			dstFile.write(buffer, 0, readed);
		}
		dstFile.flush();
		srcFile.close();
		dstFile.close();
	}
	
	private static void nioCopy(String src, String dst) throws IOException {
		RandomAccessFile srcFile = new RandomAccessFile(src, "r"); 
		RandomAccessFile dstFile = new RandomAccessFile(dst, "rw");
		FileChannel srcChannel = srcFile.getChannel();
		FileChannel dstChannel = dstFile.getChannel();
		MappedByteBuffer srcMbb = null;
		MappedByteBuffer dstMbb = null;
		long fileSize = srcChannel.size();
		System.out.println("file size " + fileSize);
		int blockCount = 10; //default value
		if(fileSize % 10 > 0) {
			blockCount++;
		}
		int position = 0;
		long size = 0;
		for(int i = 0; i < blockCount; i++) {
			System.out.println("position " + position);
			size = fileSize / blockCount;
			if(i == blockCount - 1) {
				size = fileSize - position;
			}
			System.out.println("size " + size);
			srcMbb = srcChannel.map(MapMode.READ_ONLY, position, size);
			dstMbb = dstChannel.map(MapMode.READ_WRITE, position, size);
			for(int j = 0; j < size; j++) {
				byte b = srcMbb.get(j);
				dstMbb.put(j, b);
			}
			position += size;
		}
		System.out.println("final position " + position);
		srcChannel.close();
		dstChannel.close();
		srcFile.close();
		dstFile.close();
	}

}
