package com.autowrite.bizmodel.share;


/**
 * UserSessionBean  Bean
 */

import java.util.*;

public class UserSessionBean extends DataBean {
	protected String _userID = "SUSERID";
	protected String _passWd = "SUSERPASSWD";
	protected String _mailBoxID = "SMAILBOXID";
	protected String _siteID = "SSITEID";
	protected String _classID = "SCLASSID";
	protected String _status = "SSTATUS";
	protected String _appID = "SDEFAULTAPP";

	protected String[] _services;
	protected String[] _documents;
	protected String[] _firstDocument;
	protected String _check = "F";

	protected String _systemCategory = "PROD";

	private Vector addSites = new Vector();

	public UserSessionBean() {

	}
	public String getValuesAsXML() {
		StringBuffer sret = new StringBuffer();
		sret.append("<UserInfo><" + _userID + ">" + getUserID() + "</" + _userID + ">");
		sret.append("<" + _passWd + ">" + getPassWd() + "</" + _passWd + ">");
		sret.append("<" + _mailBoxID + ">" + getMailBoxID() + "</" + _mailBoxID + ">");
		sret.append("<" + _siteID + ">" + getSiteID() + "</" + _siteID + ">");
		sret.append("<" + _classID + ">" + getClassID() + "</" + _classID + ">");
		sret.append("<" + _status + ">" + getStatus() + "</" + _status + ">");
		sret.append("<" + _appID + ">" + getApplication() + "</" + _appID + ">");
		sret.append("<SystemCategory>" + _systemCategory + "</SystemCategory>");
		sret.append("<Check>" + _check + "</Check>");
		sret.append("</UserInfo>");
		return sret.toString();
	}
	public void setValuesByXML(String xml) throws Exception {
		String sval = getTagVal(_userID, xml);
		if (sval != null)
			setUserID(sval);
		else {
			throw new Exception("No User ID. It must be wrong XML data");
		}
		sval = getTagVal(_passWd, xml);
		if (sval != null)
			setPassWd(sval);
		sval = getTagVal(_mailBoxID, xml);
		if (sval != null)
			setMailBoxID(sval);
		sval = getTagVal(_siteID, xml);
		if (sval != null)
			setSiteID(sval);
		sval = getTagVal(_classID, xml);
		if (sval != null)
			setClassID(sval);
		sval = getTagVal(_status, xml);
		if (sval != null)
			setStatus(sval);
		sval = getTagVal(_appID, xml);
		if (sval != null)
			setApplication(sval);
		sval = getTagVal("SystemCategory", xml);
		if (sval != null)
			setSystemCategory(sval);
		sval = getTagVal("Check", xml);
		if (sval != null)
			setSingleUserCheck(sval);

	}
	protected String getTagVal(String skey, String content) {
		if (content == null || skey == null)
			return null;
		int ipos = content.indexOf("<" + skey);
		String sval = null;
		if (ipos >= 0) {
			int epos = content.indexOf("</" + skey);
			sval = content.substring(ipos + skey.length() + 2, epos);
			if (sval.length() == 0)
				sval = null;
		}
		return sval;
	}

	public void setUserID(String userID) {
		set(_userID, userID);
	}

	public void setPassWd(String passWd) {
		set(_passWd, passWd);
	}

	public void setMailBoxID(String mailBoxID) {
		set(_mailBoxID, mailBoxID);
	}

	public void setSiteID(String siteID) {
		set(_siteID, siteID);

	}

	public void setClassID(String classID) {
		set(_classID, classID);
	}

	public void setStatus(String status) {
		set(_status, status);
	}

	public void setServices(String[] services) {
		this._services = services;
	}

	public void setDocuments(String[] documents) {
		this._documents = documents;
	}

	public void setApplication(String app) {
		if (app == null || app.length() == 0)
			app = "DEFAULT";
		set(_appID, app);
	}

	public void setFirstDocument(String[] firstDoc) {
		this._firstDocument = firstDoc;
	}

	public void setSingleUserCheck(String check) {
		this._check = check;
	}

	public void setSystemCategory(String systemCategory) {
		this._systemCategory = systemCategory;
	}

	public String getUserID() {
		return get(_userID);
	}

	public String getPassWd() {
		return get(_passWd);
	}

	public String getMailBoxID() {
		return get(_mailBoxID);
	}

	public String getSiteID() {
		return get(_siteID);
	}

	public String getClassID() {
		return get(_classID);
	}

	public String getStatus() {
		return get(_status);
	}

	public String[] getServices() {
		return _services;
	}

	public String[] getDocuments() {
		return this._documents;
	}

	public String[] getFirstDocument() {
		return this._firstDocument;
	}

	public String getSingleUserCheck() {
		return this._check;
	}

	public String getApplication() {
		String app = get(_appID);
		if (app == null || app.length() == 0)
			app = "DEFAULT";
		return app;
	}

	public String getSystemCategory() {
		return this._systemCategory;
	}

	public boolean isService(String service) {
		String tmp;

		for (int i = 0; this._services != null && i < this._services.length; i++) {
			tmp = _services[i];
			if (service.equals(tmp))
				return true;
		}

		// System.out.println(service + " is not a service.");
		return false;
	}

	public boolean isDocument(String document) {
		String tmp;

		for (int i = 0; this._documents != null && i < this._documents.length; i++) {
			tmp = _documents[i];
			if (document.equals(tmp))
				return true;
		}

		// System.out.println(document + " is not a document.");
		return false;
	}

}
