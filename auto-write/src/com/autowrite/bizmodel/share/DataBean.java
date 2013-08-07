package com.autowrite.bizmodel.share;

import java.util.ArrayList;

public class DataBean {
	protected ArrayList aList = null;
	protected DataTransfer curData = null;

	public DataBean() {

		aList = new ArrayList();
		curData = new DataTransfer();
		aList.add(curData);
	}

	public DataBean(DataTransfer data) {
		// aList =new ArrayList(data.getMap().values());
		int nloop = data.getMap().size();
		aList = new ArrayList(nloop);

		for (int i = 1; i <= nloop; i++) {
			aList.add(data.getObj("row" + i));
		}

	}
	public void setData(DataTransfer data) {
		// aList =new ArrayList(data.getMap().values());
		int nloop = data.getMap().size();
		aList = new ArrayList(nloop);

		for (int i = 1; i <= nloop; i++) {
			aList.add(data.getObj("row" + i));
		}

	}

	public int size() {
		return aList.size();
	}

	public void elementPosAt(int i) {

		curData = (DataTransfer) aList.get(i);
	}

	public DataTransfer getEntity(int i) {
		if (i > aList.size())
			return null;
		return (DataTransfer) aList.get(i);
	}

	public DataTransfer getCurrentDataSet() {
		return curData;
	}

	public String[] getOneValueList(String skey) {
		DataTransfer data = null;
		int nloop = aList.size();
		String[] alist = new String[nloop];
		for (int i = 0; i < nloop; i++) {
			data = getEntity(i);
			alist[i] = data.get(skey);

		}
		return alist;
	}

	public String get(String skey) {
		return (String) curData.get(skey);
	}
	public Object getObj(String skey) {
		return curData.getObj(skey.toUpperCase());
	}
	public void set(Object skey, Object obj) {
		curData.set(skey, obj);

	}
	public boolean isEmpty() {
		if (size() == 0)
			return true;
		else
			return false;
	}

}
