/**
 * @(#) FileHelper.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.comm.util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

/**
 * 文件操作工具类
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年9月22日 下午8:13:52 $
 */
public final class FileHelper {

	/**
	 * 文件转字节数组
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static byte[] fileToByteArray(String filename) throws IOException {
		FileChannel fc = null;
		try {
			fc = new RandomAccessFile(filename, "r").getChannel();
			MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0, fc.size()).load();
			byte[] result = new byte[(int) fc.size()];
			if (byteBuffer.remaining() > 0) {
				// System.out.println("remain");
				byteBuffer.get(result, 0, byteBuffer.remaining());
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if(null != fc) fc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 创建目录
	 * 
	 * @param pathName
	 */
	public static void mkDir(String path) {
		File file = new File(path);
		if (file.exists()) {
			return;
		}
		if (file.mkdir()) {
			file.setExecutable(true, false);
			file.setReadable(true, false);
			file.setWritable(true, false);
			return;
		}
		File canonFile = null;
		try {
			canonFile = file.getCanonicalFile();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		File parent = canonFile.getParentFile();
		mkDir(parent.getPath());
		mkDir(canonFile.getPath());
	}

}
