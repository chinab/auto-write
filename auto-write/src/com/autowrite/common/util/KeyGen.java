package com.autowrite.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class KeyGen {

	static protected int keyCount = 0;
	static String thisTime = "";
	protected static Random randomNumberGenerator = new Random();

	public static String getTimeKey() {
		return getTimeKey(null);
	}

	public static String getTimeKey(int nsize) {
		return getTimeKey(null, nsize);
	}

	public static String getTimeKey(String prefix) {
		// TODO Auto-generated method stub
		return getTimeKey(prefix, 0);
	}

	public static synchronized String getTimeKey(String prefix, int nsize) {
		// TODO Auto-generated method stub
		String jobId = "";
		if (prefix == null)
			prefix = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date currentTime = new Date();
		jobId = formatter.format(currentTime) + prefix;

		String seq = Integer.toString(incCount(jobId));

		StringBuffer sb = new StringBuffer();
		sb.append(jobId);
		for (int i = 0; i < nsize - seq.length(); i++) {
			sb.append("0");
		}
		sb.append(seq);

		thisTime = jobId;

		return sb.toString();
	}

	protected static int incCount(String ctime) {

		if (ctime.equals(thisTime)) {
			keyCount++;
		} else {
			keyCount = 1;
			thisTime = ctime;
		}
		return keyCount;

	}

	public static synchronized String getRandomKey(String strDomainName) {
		Date date = new Date(System.currentTimeMillis());
		String dname = strDomainName == null ? "" : "@" + strDomainName;
		String newKey = (new StringBuffer(String.valueOf(DateUtil
				.getDateFormat("yyyyMMddHHmmss"))))
				.append(Integer.toString(Math.abs(randomNumberGenerator
						.nextInt()))).append(dname).toString();
		return newKey;
	}
}
