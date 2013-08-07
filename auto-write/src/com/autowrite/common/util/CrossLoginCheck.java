package com.autowrite.common.util;

import java.util.ArrayList;
//import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.jekyll.bizmodel.share.UserSessionBean;
import com.jekyll.common.config.ReadConfig;

@SuppressWarnings("rawtypes")
public class CrossLoginCheck extends Hashtable {

	private static final long serialVersionUID = 6563494647588315149L;
	private static Hashtable idTable = new Hashtable();
	private int SLOGINTIMEOUT = 180;
	private String channel = "GLOBAL";
	private String sessionLoginTimeOut = ReadConfig.get(channel, "DEFAULT_SESSION_TIMEOUT");

	public CrossLoginCheck() {

	}

	public void PutHashTable(String UserId, String SessionId) {
		try {
			ArrayList sInfo = new ArrayList();
			sInfo.add(SessionId);
			sInfo.add(new Long(getLongTime()));

			idTable.remove(UserId);
			idTable.put(UserId, sInfo);

			System.out.println("put login user:" + UserId + "," + sInfo.toString());

		} catch (Exception ex) {
			System.out.println("put hashtable error :" + ex.getMessage());
		}
	}

	public void UpdateHashTable(String UserId) {
		try {
			List sInfo = (List) idTable.get(UserId);
			String sid = (String) sInfo.get(0);
			sInfo.set(1, new Long(getLongTime()));
		} catch (Exception ex) {
			System.out.println("update hashtable error :" + ex.getMessage());
		}
	}

	public boolean UpdateHashTable(String UserId, String SessionId) {

		boolean re = false;
		try {
			List sInfo = (List) idTable.get(UserId);
			String sid = (String) sInfo.get(0);
			if (SessionId.equals(sid)) {
				sInfo.set(1, new Long(getLongTime()));
				re = true;
			} else {
				re = false;
				System.out.println(UserId + "'s sessionid is changed, invalidate session");
			}
			// System.out.println("update login user:"+UserId+","+sInfo.toString());
		} catch (Exception ex) {
			System.out.println("update hashtable error :" + ex.getMessage());
			re = false;
		}
		return re;
	}
	public boolean UpdateHashTable(String UserId, HttpServletRequest request) {

		boolean re = false;
		try {
			String SessionId = SessionUtil.getCookieVal(request.getCookies(), "userkey");
			List sInfo = (List) idTable.get(UserId);
			String sid = (String) sInfo.get(0);
			if (SessionId.equals(sid)) {
				sInfo.set(1, new Long(getLongTime()));
				re = true;
			} else {
				re = false;
				System.out.println(UserId + "'s sessionid is changed, invalidate session");
			}
		} catch (Exception ex) {
			System.out.println("update hashtable error :" + ex.getMessage());
			re = false;
		}
		return re;
	}

	private boolean CompareUserId(String UserId, String SessionId) {
		boolean CrossId = false;

		if (sessionLoginTimeOut != null && sessionLoginTimeOut.length() > 0) {
			SLOGINTIMEOUT = Integer.parseInt(sessionLoginTimeOut);
		}

		try {
			List sInfo = (List) idTable.get(UserId);
			if (sInfo == null || sInfo.isEmpty()) {// hashtable에 아이디가 없을 경우 즉
													// 로그인하지 않았을때
				CrossId = false;
			} else {
				String sid = (String) sInfo.get(0);
				if (SessionId.equals(sid)) {// 이럴 경우는 없겠지만 로그인 되어 있지만 세션이 같을 경우
											// 즉 원래 사용자인경우
					CrossId = false;
				} else {// hashtable에 아이디가 있는경우 즉 동시 로그인 시도할경우
					Long ltime = (Long) sInfo.get(1);
					long ntime = getLongTime();
					/*
					 * System.out.println("last time:"+ltime.longValue());
					 * System.out.println("new time:"+ntime);
					 * System.out.println(
					 * "diff time:"+(ntime-ltime.longValue())/1000.0);
					 */
					if ((ntime - ltime.longValue()) > (SLOGINTIMEOUT * 1000.0)) {
						CrossId = false;
					} else
						CrossId = true;
				}
			}

			// System.out.println("Compare User:"+CrossId);

		} catch (Exception ex) {
			System.out.println("Compare hashtable error :" + ex.getMessage());
		}
		return CrossId;
	}

	public boolean LoginCheck(String UserId, String SessionId) {
		boolean chk = true;
		String usrid = UserId.trim();
		String sesid = SessionId.trim();
		// System.out.print(usrid+":"+sesid);
		try {
			if ((usrid == null) || (sesid == null)) {
				chk = false;
			} else {
				if (CompareUserId(usrid, sesid)) {
					chk = false;
				} else {
					PutHashTable(usrid, sesid);
					chk = true;
				}
			}
		} catch (Exception ex) {
			System.out.println("logincheck error :" + ex.getMessage());
		}
		return chk;
	}

	public boolean RemoveUserId(String UserId) {
		boolean ret = false;
		try {
			if (UserId != null) {
				idTable.remove(UserId);
				System.out.println("Remove User:" + UserId);
				ret = true;
			}
		} catch (Exception ex) {
			System.out.println("remove error :" + ex.getMessage());
		}

		return ret;
	}

	public boolean RemoveUserId(String UserId, String SessionId) {
		boolean ret = false;
		try {

			List sInfo = (List) idTable.get(UserId);
			if (sInfo != null && !sInfo.isEmpty()) {
				String sid = (String) sInfo.get(0);
				if (SessionId.equals(sid)) {
					idTable.remove(UserId);
					System.out.println("Remove User:" + UserId);
					ret = true;
				}
			}

		} catch (Exception ex) {
			System.out.println("remove error :" + ex.getMessage());
		}

		return ret;
	}

	public void printInfo() {
		Enumeration em = idTable.keys();
		while (em.hasMoreElements()) {
			System.out.println("login user:" + em.nextElement());
		}
	}
	private static long getLongTime() {

		// Calendar cal= Calendar.getInstance();
		// return cal.getTimeInMillis();
		return System.currentTimeMillis();

	}

	public void PutHashTable(String UserId, String SessionId, UserSessionBean user) {
		// TODO Auto-generated method stub
		try {
			ArrayList sInfo = new ArrayList();
			sInfo.add(SessionId);
			sInfo.add(new Long(getLongTime()));
			sInfo.add(user);

			idTable.remove(UserId);
			idTable.put(UserId, sInfo);

			System.out.println("put login user:" + UserId + "," + sInfo.toString());

		} catch (Exception ex) {
			System.out.println("put hashtable error :" + ex.getMessage());
		}
	}

	public UserSessionBean getSessionUser(String sid) {
		Enumeration em = idTable.elements();
		ArrayList sinfo = null;
		String skey = null;
		// long ATIME =10*60*1000;
		long ATIME = 30 * 60 * 1000;
		String stime = ReadConfig.get(channel, "DEFAULT_SESSION_TIMEOUT"); // minute
		if (stime != null && stime.length() > 0) {
			try {
				ATIME = Long.parseLong(stime) * 1000 * 60;
			} catch (Exception ee) {
			}

		}

		UserSessionBean user = null;
		while (em.hasMoreElements()) {
			sinfo = (ArrayList) em.nextElement();
			skey = (String) sinfo.get(0);
			if (skey.equals(sid)) {
				long ltime = ((Long) sinfo.get(1)).longValue();
				long diff = System.currentTimeMillis() - ltime;
				if (diff > ATIME) {
					System.out.println("[" + sid + "]Session is expired. exceeding time[" + diff + "]");
					return null;
				}

				System.out.println("[" + sid + "]" + diff);

				if (sinfo.size() > 2) {
					sinfo.set(1, new Long(getLongTime()));
					user = (UserSessionBean) sinfo.get(2);

				}
			}
		}
		return user;
	}

}