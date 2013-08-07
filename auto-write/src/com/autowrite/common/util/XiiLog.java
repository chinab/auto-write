package com.autowrite.common.util;

import java.io.File;

public class XiiLog extends LogImpl {

	String logDir = null;
	long logSize = 2000000;
	String logLevel = "D";
	StringBuffer logBuffer = null;
	int bufSize = 2000;
	String LF = "\r\n";
	boolean useBuf = true;
	String curLogFile = null;
	String SP = File.separator;

	public XiiLog() {
		logBuffer = new StringBuffer();
		logDir = XIIConstant.DIR_LOG;
		logDir = FileUtil.buildDatePathDir(logDir);
	}

	public void setLogLevel(String s) {
		if (s != null)
			this.logLevel = s;
	}

	public void setUseBuffer(boolean bflag) {
		this.useBuf = bflag;
	}

	public void set(String who, String uniqueId, String level, String desc,
			String flush) {
		// System.out.println(desc);
		String log = "[" + who + "][" + uniqueId + "][" + desc;
		boolean bflush = false;
		if ("T".equalsIgnoreCase(flush))
			bflush = true;
		set(log, level, bflush);
	}

	public void set(String who, String uniqueId, String level, Exception ex) {
		String slog = "[" + who + "][" + uniqueId + "]["
				+ StringUtils.getExceptionTrace(ex);
		set(slog, level);
	}

	public void set(Object who, String uniqueId, String level, String msg) {
		// System.out.println(msg);
		String log = "[" + who.getClass() + "][" + uniqueId + "][" + msg;
		set(log, level);
	}

	public void set(Object who, String uniqueId, String level, Exception ex) {
		String slog = "[" + who.getClass() + "][" + uniqueId + "]["
				+ StringUtils.getExceptionTrace(ex);
		set(slog, level);
	}

	public void set(String who, String uniqueId, String level, String desc) {
		set(who, uniqueId, level, desc, "F");
	}

	public void set(String ln, String lval) {
		set(ln, lval, false);
	}

	public void set(String ln, String lval, boolean bflush) {
		// System.out.println(ln);
		if (this.logLevel.equals(LogImpl.INFO))
			if (LogImpl.DEBUG.equals(lval))
				return;
			else if (this.logLevel.equals(LogImpl.WARN)) {
				if (LogImpl.DEBUG.equals(lval) || LogImpl.INFO.equals(lval))
					return;
			} else if (this.logLevel.equals(LogImpl.ERROR)) {
				if (LogImpl.DEBUG.equals(lval) || LogImpl.INFO.equals(lval)
						|| LogImpl.WARN.equals(lval))
					return;
			}
		String slog = "[" + DateUtil.getCurrentTime() + "][" + lval + "]" + ln
				+ LF;
		if (useBuf && !bflush) {
			this.logBuffer.append(slog);
			if (this.logBuffer.length() > this.bufSize) {
				writeBuf();
			}
		} else {
			writeLog(slog);
		}
	}

	public void set(String ln, Throwable ex) {
		if (ex == null)
			return;
		// System.out.println("xiilog exception:"+ex.getMessage());
		set(StringUtils.getExceptionTrace(ex), LogImpl.ERROR);
	}

	public void log(String jobid, String who, String code, String msg) {
		String slog = "[" + who + "][" + jobid + "][" + code + "]" + msg;
		set(slog, LogImpl.DEBUG);
	}

	public String log(String jobid, String who, String code, String msg,
			String flush) {
		String slog = "[" + who + "][" + jobid + "][" + code + "]" + msg;
		boolean bflush = false;
		if ("T".equalsIgnoreCase(flush))
			bflush = true;
		set(slog, LogImpl.DEBUG, bflush);
		return this.curLogFile;
	}

	public void info(String string) {
		// TODO Auto-generated method stub
		set(string, LogImpl.INFO);
	}

	public void error(String string) {
		// TODO Auto-generated method stub
		set(string, LogImpl.ERROR);
	}

	public void debug(String string) {
		// TODO Auto-generated method stub
		set(string, LogImpl.DEBUG);
	}

	public void warn(String string) {
		// TODO Auto-generated method stub
		set(string, LogImpl.WARN);
	}

	public void info(Object msg) {
		info(msg.toString());
	}

	public void error(Object msg) {
		error(msg.toString());
	}

	public void debug(Object msg) {
		debug(msg.toString());
	}

	public void warn(Object msg) {
		warn(msg.toString());
	}

	protected synchronized void writeBuf() {

		try {
			// System.out.println("log buf :"+this.logBuffer.length());
			if (this.logBuffer.length() < this.bufSize)
				return;
			writeLog(this.logBuffer.toString());
			this.logBuffer.delete(0, this.logBuffer.length());
		} catch (Exception ex) {

		}
	}

	protected void writeLog(String log) {
		try {
			logDir = FileUtil.buildDatePathDir(XIIConstant.DIR_LOG) + SP;
			String logSeq = logDir + "logSeq";
			File f = new File(logSeq);
			String sseq = "1";
			if (f.exists()) {
				sseq = new String(FileUtil.readFile(f));

			} else
				FileUtil.saveFile(logSeq, sseq.getBytes());

			String logFile = DateUtil.getToday() + "_" + sseq + ".log";
			File lf = new File(logDir + logFile);
			if (lf.length() > this.logSize) {
				int iseq = Integer.parseInt(sseq);
				iseq++;
				sseq = Integer.toString(iseq);
				logFile = DateUtil.getToday() + "_" + sseq + ".log";
				FileUtil.saveFile(logSeq, sseq.getBytes());
			}
			curLogFile = logDir + logFile;
			FileUtil.saveFile(logDir + logFile, log.getBytes(), true);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
