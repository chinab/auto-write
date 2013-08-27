package com.autowrite.common.config;

public class Constant {
	public static final String TAG_CONDI = "Condition";
	public static final String TAG_QUERY = "Query";
	public static final String TAG_PARAM = "param";
	public static final String TAG_REF = "Reference";
	public static final String TAG_FIELDVALUE = "FieldValues";

	public static final String TAG_MESSAGE = "MessageNumber";
	public static final String TAG_DOCUMENT = "DocumentNumber";
	public static final String TAG_DOCTYPE = "MessageFunctionCode";
	public static final String TAG_DOCNAME = "MessageTypeIdentifier";
	public static final String TAG_MAILBOXID = "MessageSenderIdentifier";
	public static final String TAG_PARTNERID = "MessageReceiverIdentifier";
	public static final String TAG_GROUP = "GroupElement";
	public static final String TAG_ELEMENT = "Element";

	public static final String ATT_NAME = "name";
	public static final String ATT_FIELD = "field";
	public static final String ATT_OPER = "oper";
	public static final String ATT_PREFIX = "prefix";
	public static final String ATT_VALUE = "value";
	public static final String ATT_FORWARD = "forward";
	public static final String ATT_IOTYPE = "IOtype";
	public static final String ATT_DATATYPE = "type";
	public static final String ATT_SEQ = "seq";
	public static final String ATT_MAX = "max";
	public static final String ATT_MIN = "min";
	public static final String ATT_LENGTH = "length";
	public static final String ATT_CONDI = "condi";

	public static final String CF_CHANNEL = "channel";
	public static final String CF_PATH = "path";

	public static final boolean DEBUG = true;

	public static final String LOGSTYLE_FILE = "file";
	public static final String LOGSTYLE_DB = "db";
	public static final String LOGSTYLE_CON = "console";

	public static final int LOGLEVEL = 0;
	public static final int LDEBUG = 1;
	public static final int LINFO = 2;
	public static final int LERROR = 3;
	public static final int LWARN = 4;

	public static final String ERROR_ = "error";

	public static final String LOCATION_SCHEMA = "webapps/Server/Schema/";
	public static final String LOCATION_TXXSL = "webapps/Server/TxXsl/";
	public static final String LOCATION_XSLTP = "webapps/Server/xslTemplate/";
	public static final String LOCATION_JSFILE = "webapps/Server/jsfiles/";
	public static final String LOCATION_XMLTP = "webapps/Server/xmlTemplate/";
	public static final String LOCATION_TEMP = "temp/";

	public static final String URL_SCHEMA = "Server/Schema/";

	public static final String GO_INDEX = "/index.html";
	public static final String GO_ADMINDEX = "/admin/index.html";
	public static final String GO_SYSADMINDEX = "/sysadmin/index.html";

	public static final String USER_KEY = "LoginUser";
	public static final String ADMUSER_KEY = "LoginAdm";
	public static final String SYSADMUSER_KEY = "LoginSysAdm";

	public static final String EP_USER_KEY = "EP" + USER_KEY;
	public static final String EP_ADMUSER_KEY = "EP" + ADMUSER_KEY;
	public static final String EP_SYSADMUSER_KEY = "EP" + SYSADMUSER_KEY;

	public static final String STLS_HASH = "u4U4tOjc9/XW9WEZ3kAh1A==";

	public static final String USER_SESSION_KEY = USER_KEY + STLS_HASH;
	public static final String ADMUSER_SESSION_KEY = ADMUSER_KEY + STLS_HASH;
	public static final String SYSADMUSER_SESSION_KEY = SYSADMUSER_KEY + STLS_HASH;

	public static final String EP_USER_SESSION_KEY = EP_USER_KEY + STLS_HASH;
	public static final String EP_ADMUSER_SESSION_KEY = EP_ADMUSER_KEY + STLS_HASH;
	public static final String EP_SYSADMUSER_SESSION_KEY = EP_SYSADMUSER_KEY + STLS_HASH;

	public static final String STATUS_ACTIVE = "ACTIVE";
	public static final String STATUS_SUSPEND = "SUSPEND";
	public static final String STATUS_END = "END";
	public static final String STATUS_REQ = "REQUEST";
	public static final String STATUS_LOCKED = "LOCKED";

	public static final String TP_INSESSION = "tpData";

	public static final String ERROR = "errorCode";
	public static final String SUCCESS = "successCode";
	public static final String SESSION_ID = "sessionId";
	public static final String PARA_SESSION_ID = "para_sessionId";
	public static final String HISTORY_BACK = "back";
	public static final String APPLICATION_ID = "applicationId";

	/* admin & user class id */
	public static final String SYSTEMADMIN = "SA";
	public static final String SAMSUNGADMIN = "SECA";
	public static final String SAMSUNGUSER = "SECU";
	public static final String PARTNERADMIN = "PTNA";
	public static final String PARTNERUSER = "PTNU";
	public static final String TPLADMIN = "TPLA";
	public static final String TPLUSER = "TPLU";
	/* partner mailbox class */
	public static final String SAMSUNG = "SEC";
	public static final String TPL = "TPL";
	public static final String PARTNER = "PTN";
	/* Receiver Type Code */
	public static final String SINGLERECVER = "S";
	public static final String MULTIRECVER = "M";
	public static final String BOTH = "B";
	
	public static final long BOARD_PAGE_SIZE = 30;
	public static final long MEMO_PAGE_SIZE = 8;
	public static final long BOARD_WRITE_LIMIT = 5;
	public static final long MEMO_WRITE_LIMIT = 100;
	
	public static final String LOGIN_POINT = "5";
	public static final String MEMO_WRITE_POINT = "-1";
	public static final String REPLY_WRITE_POINT = "1";
	public static final String BOARD_RECOMMEND_POINT = "1";
	public static final String BOARD_REJECT_POINT = "-1";
	
	public static final String LOGIN_CATEOGRY = "LOGIN";
	public static final String LOGOUT_CATEOGRY = "LOGOUT";
	
	
	
	public static final String AUTOWRITE_SERVICE_PACKAGE = "com.autowrite.service";
	
}
