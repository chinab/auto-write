package com.autowrite.common.util;

import java.io.File;

public class XIIConstant {

	public static boolean DEBUG = false;
	public static boolean isShutdown = false;
	public static String THISHOME = null;
	public static String FSP = File.separator;

	public static String DIR_LOG = "Log" + FSP;
	public static String DIR_RESOURCE = "Resource" + FSP;
	public static String DIR_CONFIG = "Resource" + FSP + "Config" + FSP;
	public static String DIR_RULES = "Resource" + FSP + "Rules" + FSP;
	public static String DIR_SECURITY = "Resource" + FSP + "Security" + FSP;
	public static String DIR_MESSAGE = "Message" + FSP;
	public static String DIR_MESSAGE_IN = "Message" + FSP + "In" + FSP;
	public static String DIR_MESSAGE_RETRY = "Message" + FSP + "Retry" + FSP;
	public static String DIR_MESSAGE_ERROR = "Message" + FSP + "Error" + FSP;
	public static String DIR_DATA = "Data" + FSP;
	public static String DIR_PAYLOAD = "Data" + FSP + "Payload" + FSP;
	public static String DIR_PACKAGE = "Data" + FSP + "Package" + FSP;
	public static String DIR_TEMP = "Data" + FSP + "Temp" + FSP;
	public static String DIR_RETRY = "Data" + FSP + "Temp" + FSP + "Retry"
			+ FSP;
	public static String DIR_TEMP_RECV = "Data" + FSP + "Temp" + FSP + "Recv"
			+ FSP;
	public static String DIR_TEMP_SEND = "Data" + FSP + "Temp" + FSP + "Send"
			+ FSP;

	public static void setHome(String home) {
		// TODO Auto-generated method stub
		if (home.endsWith("\\") || home.endsWith("/"))
			THISHOME = home.substring(0, home.length() - 1) + FSP;
		else
			THISHOME = home + FSP;

		DIR_RESOURCE = THISHOME + DIR_RESOURCE;
		DIR_CONFIG = THISHOME + DIR_CONFIG;
		DIR_SECURITY = THISHOME + DIR_SECURITY;
		DIR_MESSAGE = THISHOME + DIR_MESSAGE;
		DIR_MESSAGE_IN = THISHOME + DIR_MESSAGE_IN;
		DIR_MESSAGE_RETRY = THISHOME + DIR_MESSAGE_RETRY;
		DIR_MESSAGE_ERROR = THISHOME + DIR_MESSAGE_ERROR;
		DIR_DATA = THISHOME + DIR_DATA;
		DIR_PAYLOAD = THISHOME + DIR_PAYLOAD;
		DIR_LOG = THISHOME + DIR_LOG;
		DIR_TEMP = THISHOME + DIR_TEMP;
		DIR_RETRY = THISHOME + DIR_RETRY;
		DIR_TEMP_RECV = THISHOME + DIR_TEMP_RECV;
		DIR_TEMP_SEND = THISHOME + DIR_TEMP_SEND;
		DIR_RULES = THISHOME + DIR_RULES;

	}

	public static void setDataHome(String home) {
		DIR_DATA = home + DIR_DATA;
		DIR_PAYLOAD = home + DIR_PAYLOAD;
		DIR_PACKAGE = home + DIR_PACKAGE;
	}

	public static void setLogHome(String home) {
		DIR_LOG = home + DIR_LOG;
	}
}
