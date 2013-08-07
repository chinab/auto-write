package com.autowrite.common.util;

public class xslFn {

	public static String getKey(String prefix) throws Exception {
		return prefix + "_" + KeyGen.getTimeKey() + "M";
		// return name+"_2010102910000";

	}

	/**
	 * 넘어온 값을의 시간을 delimeter로 구분 - 구분자(gbn): TIME[시간], DATE[날짜] return :
	 * 예)시간-17:30, 날짜-2005/04/05
	 */
	public static String convertVal(String val, String delimeter, String gbn)
			throws Exception {
		String resultVal = null;
		if (val == "" || val.equals("00000000"))
			return "";

		try {
			if (gbn.equals("DATE")) {
				if (val.length() < 8)
					return val;
				resultVal = val.substring(0, 4) + delimeter
						+ val.substring(4, 6) + delimeter + val.substring(6, 8);
			} else if (gbn.equals("TIME")) {
				if (val.length() < 6)
					return val;
				resultVal = val.substring(0, 2) + delimeter
						+ val.substring(2, 4) + delimeter + val.substring(4, 6);
			} else if (gbn.equals("DATETIME")) {
				if (val.length() < 12)
					return val;
				resultVal = val.substring(0, 4) + delimeter
						+ val.substring(4, 6) + delimeter + val.substring(6, 8)
						+ " " + val.substring(8, 10) + ":"
						+ val.substring(10, 12);
			}
		} catch (Exception ex) {
			ex.getStackTrace();
			throw ex;
		}

		return resultVal;
	}

	/**
	 * 금액(수치) 데이터를 "," 로 구분하기
	 */
	public static String convertAmount(String amountVal) throws Exception {
		if (amountVal == null)
			return "";

		String m_last = "";
		int pos = amountVal.indexOf(".");
		String m_prev = null;

		if (pos != -1) {
			m_last = amountVal.substring(pos, amountVal.length());
			m_prev = amountVal.substring(0, pos);
		} else {
			m_prev = amountVal;
		}

		int mlength = m_prev.length();
		String m_val = "";

		while (mlength >= 3) {
			m_val = m_prev.substring(m_prev.length() - 3, m_prev.length())
					+ m_val;
			m_prev = m_prev.substring(0, m_prev.length() - 3);
			if (mlength > 3)
				m_val = "," + m_val;
			mlength = m_prev.length();
		}

		return m_prev + m_val + m_last;
	}

	/**
	 * 문자열자르기 val : 문자열 , val1 : 시작점, val2 : 끝점
	 */
	public static String getSubstr(String val, int val1, int val2)
			throws Exception {
		String result = null;
		if (val == null || "".equals(val)) {
			result = "";
		} else {
			result = val.substring(val1, val2);
		}
		return result;
	}

	/*
	 * ============================================ function : cutSizeOver() 설명
	 * : Description이 너무 긴 경우 잘라서 일정 자리까지 보여주는 함수
	 * ============================================
	 */

	/**
	 * 문자열자르기2 desc. 자르기...
	 */
	public static String cutSizeOver(String val, int size) throws Exception {
		int len = val.length();

		if (len > size)
			val = val.substring(0, size) + "...";

		return val;
	}

	public static String getCurTime(String sformat) {
		return DateUtil.getDateFormat(sformat);
	}

	public static String getDeli(String deli) {
		StringBuffer sb = new StringBuffer();
		if ("linefeed".equalsIgnoreCase(deli)) {
			sb.append(new String(new byte[] { 0x0d, 0x0a }));
		} else if ("unixlinefeed".equalsIgnoreCase(deli)) {
			sb.append(new String(new byte[] { 0x0a }));
		} else if ("^1".equals(deli)) {
			sb.append(new String(new byte[] { 0x1 }));
		} else if ("^2".equals(deli)) {
			sb.append(new String(new byte[] { 0x2 }));
		} else if ("^3".equals(deli)) {
			sb.append(new String(new byte[] { 0x3 }));
		}
		return sb.toString();
	}
	/**
	 * 루핑시 루핑되지 않는 항목들은 처음 한번만 Display
	 */
	/*
	 * public static boolean getElmVal(){ var no = childNumber(this);
	 * 
	 * if(no == 1) return true; else return false;
	 * 
	 * }
	 */

}
