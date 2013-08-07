package com.autowrite.common.util;

import org.apache.log4j.Logger;

public class LogImpl {

	public final static String INFO = "I";
	public final static String ERROR = "E";
	public final static String DEBUG = "D";
	public final static String WARN = "W";
	protected Logger logger = Logger.getLogger(this.getClass());

	public void setLogLevel(String s) {

	}

	public void setUseBuffer(boolean bflag) {

	}

	public void set(String who, String uniqueId, String level, String desc,
			String flush) {
		if (DEBUG.equals(level))
			logger.debug(desc);
		else if (INFO.equals(level))
			logger.info(desc);
		else if (WARN.equals(level))
			logger.warn(desc);
		else if (ERROR.equals(level))
			logger.error(desc);
		System.out.println(desc);
	}

	public void set(String who, String uniqueId, String level, Exception ex) {
		logger.error(who + ":" + uniqueId, ex);
	}

	public void set(Object who, String uniqueId, String level, String desc) {
		if (DEBUG.equals(level))
			logger.debug(desc);
		else if (INFO.equals(level))
			logger.info(desc);
		else if (WARN.equals(level))
			logger.warn(desc);
		else if (ERROR.equals(level))
			logger.error(desc);
		System.out.println(desc);
	}

	public void set(Object who, String uniqueId, String level, Exception ex) {
		logger.error(who + ":" + uniqueId, ex);
		ex.printStackTrace();
	}

	public void set(String who, String uniqueId, String level, String desc) {
		set(who, uniqueId, level, desc, "F");
	}

	public void set(String ln, String level) {
		if (DEBUG.equals(level))
			logger.debug(ln);
		else if (INFO.equals(level))
			logger.info(ln);
		else if (WARN.equals(level))
			logger.warn(ln);
		else if (ERROR.equals(level))
			logger.error(ln);
		System.out.println(ln);
	}

	public void set(String ln, Throwable ex) {
		logger.error(ln, ex);
		// ex.printStackTrace();
	}

	public void log(String jobid, String who, String code, String msg) {
		logger.debug(msg);
		System.out.println(msg);
	}

	public String log(String jobid, String who, String code, String msg,
			String flush) {
		logger.debug(msg);
		System.out.println(msg);
		return null;
	}

	public void info(String string) {
		// TODO Auto-generated method stub
		logger.info(string);
	}

	public void error(String string) {
		// TODO Auto-generated method stub
		logger.error(string);
	}

	public void debug(String string) {
		// TODO Auto-generated method stub
		logger.debug(string);
	}

	public void warn(String string) {
		// TODO Auto-generated method stub
		logger.warn(string);
	}

	public void info(Object msg) {
		logger.info(msg);
	}

	public void error(Object msg) {
		logger.error(msg);
	}

	public void debug(Object msg) {
		logger.debug(msg);
	}

	public void warn(Object msg) {
		logger.warn(msg);
	}
}
