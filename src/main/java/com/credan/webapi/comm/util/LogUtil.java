package com.credan.webapi.comm.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LogUtil {
	/**
	 * 获取堆栈信息
	 * 
	 * @param e
	 * @return
	 */
	@SuppressWarnings("unused")
	public static String getTrace(Exception e) {
		Throwable t = e.getCause();
		if (null == t) {
			t = e;
		}
		if (null != t) {
			StringWriter stringWriter = new StringWriter();
			PrintWriter writer = new PrintWriter(stringWriter);
			t.printStackTrace(writer);
			StringBuffer buffer = stringWriter.getBuffer();
			return buffer.toString();
		}
		return null;
	}

	public static String getTrace(Throwable e) {
		if (null != e) {
			StringWriter stringWriter = new StringWriter();
			PrintWriter writer = new PrintWriter(stringWriter);
			e.printStackTrace(writer);
			StringBuffer buffer = stringWriter.getBuffer();
			return buffer.toString();
		}
		return null;
	}
}
